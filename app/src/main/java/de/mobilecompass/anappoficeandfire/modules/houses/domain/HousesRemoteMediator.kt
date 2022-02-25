package de.mobilecompass.anappoficeandfire.modules.houses.domain

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import de.mobilecompass.anappoficeandfire.modules.houses.database.HousesLocalDatasource
import de.mobilecompass.anappoficeandfire.modules.houses.database.models.HouseDB
import de.mobilecompass.anappoficeandfire.modules.houses.network.HousesRemoteDataSource
import de.mobilecompass.anappoficeandfire.modules.houses.network.models.asHousesDB
import timber.log.Timber
import java.io.InvalidObjectException

@OptIn(ExperimentalPagingApi::class)
class HousesRemoteMediator(
    val initialUrl: String,
    val localDatasource: HousesLocalDatasource,
    val remoteDataSource: HousesRemoteDataSource
) : RemoteMediator<Int, HouseDB>() {

    // ----------------------------------------------------------------------------
    // region Inner types
    // ----------------------------------------------------------------------------

    // ----------------------------------------------------------------------------
    // endregion
    // ----------------------------------------------------------------------------


    companion object {
        // ----------------------------------------------------------------------------
        // region Constants
        // ----------------------------------------------------------------------------

        val LOG_TAG: String = HousesRemoteMediator::class.java.simpleName

        // ----------------------------------------------------------------------------
        // endregion
        // ----------------------------------------------------------------------------

        // ----------------------------------------------------------------------------
        // region Convenience methods
        // ----------------------------------------------------------------------------

        // ----------------------------------------------------------------------------
        // endregion
        // ----------------------------------------------------------------------------
    }

    // ----------------------------------------------------------------------------
    // region Public properties
    // ----------------------------------------------------------------------------

    // ----------------------------------------------------------------------------
    // endregion
    // ----------------------------------------------------------------------------

    // ----------------------------------------------------------------------------
    // region Internal properties
    // ----------------------------------------------------------------------------

    // ----------------------------------------------------------------------------
    // endregion
    // ----------------------------------------------------------------------------

    // ----------------------------------------------------------------------------
    // region Protected properties
    // ----------------------------------------------------------------------------

    // ----------------------------------------------------------------------------
    // endregion
    // ----------------------------------------------------------------------------

    // ----------------------------------------------------------------------------
    // region Private properties
    // ----------------------------------------------------------------------------

    // ----------------------------------------------------------------------------
    // endregion
    // ----------------------------------------------------------------------------

    // ----------------------------------------------------------------------------
    // region Init
    // ----------------------------------------------------------------------------

    init {

    }

    // ----------------------------------------------------------------------------
    // endregion
    // ----------------------------------------------------------------------------

    // ----------------------------------------------------------------------------
    // region System/Overridden methods
    // ----------------------------------------------------------------------------

    override suspend fun initialize() =
        if (localDatasource.getHousesCount() == 0)
            InitializeAction.LAUNCH_INITIAL_REFRESH
        else
            InitializeAction.SKIP_INITIAL_REFRESH

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, HouseDB>
    ): MediatorResult {
        Timber.d("loadType: $loadType")

        // Get URL to load or return a result from here
        val url = when (loadType) {
            LoadType.REFRESH -> initialUrl
            LoadType.PREPEND -> return MediatorResult.Success(true)
            LoadType.APPEND -> {
                val lastItem = state.lastItemOrNull() ?: run {
                    // FIX or NEEDED?: somehow state.pages has no data
                    // but there is data already in the database after the initial REFRESH.
                    //
                    // So set endOfPaginationReached to false if we
                    // are displaying nothing but data is in the DB.

                    val dbEntryCount = localDatasource.getHousesCount()
                    val endOfPaginationReached = dbEntryCount == 0

                    Timber.d("lastItemOrNull is null; endOfPaginationReached: $endOfPaginationReached")
                    return MediatorResult.Success(endOfPaginationReached)
                }
                val keys = lastItem.let { house ->
                    localDatasource.getRemoteKeysByHouseId(house.id)
                }
                    ?: throw InvalidObjectException("No key entry found for append at state ${state.anchorPosition}")
                keys.nextUrl ?: return MediatorResult.Success(true)
            }
        }

        // Load remote content from URL

        val housesData = remoteDataSource.getHouses(url).getOrElse {
            return@load MediatorResult.Error(it)
        }

        // If a refresh happens, delete everything from the database
        // TODO: improve, to make just an update call with lastUpdateDate from the Header

        if (loadType == LoadType.REFRESH)
            localDatasource.deleteAll()

        // Insert remote content into database

        localDatasource.insertHousesAndRemoteKeys(housesData.list.asHousesDB(), housesData.asRemoteKeysDB())

        // Check if end of pagination is reached

        val endOfPaginationReached = housesData.nextPageUrl == null

        Timber.d("endOfPaginationReached: $endOfPaginationReached")

        return MediatorResult.Success(endOfPaginationReached)
    }

    // ----------------------------------------------------------------------------
    // endregion
    // ----------------------------------------------------------------------------

    // ----------------------------------------------------------------------------
    // region Public methods
    // ----------------------------------------------------------------------------

    // ----------------------------------------------------------------------------
    // endregion
    // ----------------------------------------------------------------------------

    // ----------------------------------------------------------------------------
    // region Protected methods
    // ----------------------------------------------------------------------------

    // ----------------------------------------------------------------------------
    // endregion
    // ----------------------------------------------------------------------------

    // ----------------------------------------------------------------------------
    // region Private methods
    // ----------------------------------------------------------------------------

    // ----------------------------------------------------------------------------
    // endregion
    // ----------------------------------------------------------------------------

    // ----------------------------------------------------------------------------
    // region Interfaces/Listener
    // ----------------------------------------------------------------------------

    // ----------------------------------------------------------------------------
    // endregion
    // ----------------------------------------------------------------------------

}