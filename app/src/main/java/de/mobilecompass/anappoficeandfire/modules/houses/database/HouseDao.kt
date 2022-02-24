package de.mobilecompass.anappoficeandfire.modules.houses.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
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

    @Query("SELECT * FROM houses ORDER BY id")
    fun pagingSource(): PagingSource<Int, HouseDB>

    @Query("DELETE FROM houses")
    suspend fun deleteAll()

    // ----------------------------------------------------------------------------
    // endregion
    // ----------------------------------------------------------------------------
}