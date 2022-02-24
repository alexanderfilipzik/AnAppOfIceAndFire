package de.mobilecompass.anappoficeandfire.modules.houses.database

import androidx.paging.PagingSource
import androidx.room.*
import de.mobilecompass.anappoficeandfire.modules.houses.database.models.HouseDB

@Dao
interface HouseDao {

    // ----------------------------------------------------------------------------
    // region Properties
    // ----------------------------------------------------------------------------

    // ----------------------------------------------------------------------------
    // endregion
    // ----------------------------------------------------------------------------

    // ----------------------------------------------------------------------------
    // region Methods
    // ----------------------------------------------------------------------------

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(houses: List<HouseDB>)

    @Query("SELECT * FROM houses ORDER BY id")
    suspend fun getAll(): List<HouseDB>

    @Query("SELECT COUNT(id) FROM houses")
    suspend fun getCounter(): Int

    @Query("SELECT * FROM houses ORDER BY id")
    fun pagingSource(): PagingSource<Int, HouseDB>

    @Query("DELETE FROM houses")
    suspend fun deleteAll()

    // ----------------------------------------------------------------------------
    // endregion
    // ----------------------------------------------------------------------------
}