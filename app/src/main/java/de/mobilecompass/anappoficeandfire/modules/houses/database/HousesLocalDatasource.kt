package de.mobilecompass.anappoficeandfire.modules.houses.database

import androidx.paging.PagingSource
import de.mobilecompass.anappoficeandfire.modules.houses.database.models.HouseDB
import de.mobilecompass.anappoficeandfire.modules.houses.database.models.HouseRemoteKeysDB

interface HousesLocalDatasource {

    // ----------------------------------------------------------------------------
    // region Properties
    // ----------------------------------------------------------------------------

    // ----------------------------------------------------------------------------
    // endregion
    // ----------------------------------------------------------------------------

    // ----------------------------------------------------------------------------
    // region Methods
    // ----------------------------------------------------------------------------

    suspend fun insertHouses(houses: List<HouseDB>)

    suspend fun insertRemoteKeys(remoteKeys: List<HouseRemoteKeysDB>)

    suspend fun getHouse(id: Long): HouseDB?

    suspend fun getRemoteKeysByHouseId(id: Long): HouseRemoteKeysDB?

    suspend fun getHousesCount(): Int

    suspend fun deleteAll()

    fun pagingSource(): PagingSource<Int, HouseDB>

    // ----------------------------------------------------------------------------
    // endregion
    // ----------------------------------------------------------------------------
}