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

    suspend fun getRemoteKeysByHouseId(id: String): HouseRemoteKeysDB?

    suspend fun deleteAll()

    fun pagingSource(): PagingSource<Int, HouseDB>

    // ----------------------------------------------------------------------------
    // endregion
    // ----------------------------------------------------------------------------
}