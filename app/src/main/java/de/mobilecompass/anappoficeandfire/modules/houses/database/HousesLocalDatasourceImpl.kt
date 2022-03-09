package de.mobilecompass.anappoficeandfire.modules.houses.database

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.withTransaction
import de.mobilecompass.anappoficeandfire.modules.houses.database.models.HouseDB
import de.mobilecompass.anappoficeandfire.modules.houses.database.models.HouseRemoteKeysDB
import javax.inject.Inject

class HousesLocalDatasourceImpl @Inject constructor(private val database: HouseDatabase): HousesLocalDatasource {

    companion object {
        val LOG_TAG: String = HousesLocalDatasourceImpl::class.java.simpleName
    }

    override suspend fun insertHousesAndRemoteKeys(houses: List<HouseDB>, remoteKeys: List<HouseRemoteKeysDB>) =
        database.withTransaction {
            database.houseRemoteKeysDao.insertAll(remoteKeys)
            database.houseDao.insertAll(houses)
        }

    override fun getHouse(url: String): LiveData<HouseDB?> = database.houseDao.getHouse(url)

    override suspend fun getHouses(): List<HouseDB> =
        database.withTransaction {
            database.houseDao.getAll()
        }

    override suspend fun getRemoteKeysByHouseId(id: Long): HouseRemoteKeysDB? =
        database.withTransaction {
            database.houseRemoteKeysDao.remoteKeysByHouseId(id)
        }

    override suspend fun getHousesCount(): Int =
        database.withTransaction {
            database.houseDao.getCount()
        }

    override suspend fun deleteAll() =
        database.withTransaction {
            database.houseDao.deleteAll()
            database.houseRemoteKeysDao.deleteAll()
        }

    override fun pagingSource(): PagingSource<Int, HouseDB> = database.houseDao.pagingSource()
}