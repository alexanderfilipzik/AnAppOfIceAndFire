package de.mobilecompass.anappoficeandfire.modules.houses

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import de.mobilecompass.anappoficeandfire.modules.houses.database.HousesLocalDatasource
import de.mobilecompass.anappoficeandfire.modules.houses.database.models.HouseDB
import de.mobilecompass.anappoficeandfire.modules.houses.database.models.asHouse
import de.mobilecompass.anappoficeandfire.modules.houses.domain.HousesRemoteMediator
import de.mobilecompass.anappoficeandfire.modules.houses.domain.model.House
import de.mobilecompass.anappoficeandfire.modules.houses.network.HousesRemoteDataSource
import javax.inject.Inject

class HousesRepositoryImpl @Inject constructor(
    private val localDatasource: HousesLocalDatasource,
    private val remoteDataSource: HousesRemoteDataSource
): HousesRepository {

    companion object {
        val LOG_TAG: String = HousesRepositoryImpl::class.java.simpleName

        const val pageSize = 50
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun pager(): Pager<Int, HouseDB> = Pager(
        config = PagingConfig(pageSize, enablePlaceholders = true),
        remoteMediator = HousesRemoteMediator(
            "https://www.anapioficeandfire.com/api/houses?pageSize=$pageSize",
            localDatasource,
            remoteDataSource
        )
    ) {
        localDatasource.pagingSource()
    }

    // TODO: load from web if not in DB
    override fun getHouse(houseUrl: String): LiveData<House?> = Transformations.map(localDatasource.getHouse(houseUrl)) {
        it?.asHouse()
    }
}