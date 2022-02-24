package de.mobilecompass.anappoficeandfire.modules.houses.database

import androidx.paging.PagingSource
import de.mobilecompass.anappoficeandfire.modules.houses.database.models.HouseDB
import de.mobilecompass.anappoficeandfire.modules.houses.database.models.HouseRemoteKeysDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HousesLocalDatasourceImpl @Inject constructor(private val database: HouseDatabase): HousesLocalDatasource {

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

        val LOG_TAG: String = HousesLocalDatasourceImpl::class.java.simpleName

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

    override suspend fun insertHouses(houses: List<HouseDB>) =
        withContext(Dispatchers.IO) {
            database.houseDao.insertAll(houses)
        }

    override suspend fun insertRemoteKeys(remoteKeys: List<HouseRemoteKeysDB>) =
        withContext(Dispatchers.IO) {
            database.houseRemoteKeysDao.insertAll(remoteKeys)
        }

    override suspend fun getRemoteKeysByHouseId(id: String): HouseRemoteKeysDB? =
        withContext(Dispatchers.IO) {
            database.houseRemoteKeysDao.remoteKeysByHouseId(id)
        }

    override suspend fun deleteAll() =
        withContext(Dispatchers.IO) {
            database.houseDao.deleteAll()
            database.houseRemoteKeysDao.deleteAll()
        }

    override fun pagingSource(): PagingSource<Int, HouseDB> = database.houseDao.pagingSource()

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