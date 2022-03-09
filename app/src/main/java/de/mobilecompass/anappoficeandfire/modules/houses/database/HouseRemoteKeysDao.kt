package de.mobilecompass.anappoficeandfire.modules.houses.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import de.mobilecompass.anappoficeandfire.modules.houses.database.models.HouseRemoteKeysDB

@Dao
interface HouseRemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKeys: List<HouseRemoteKeysDB>)

    @Query("SELECT * FROM houseRemoteKeys ORDER BY houseId")
    suspend fun getAll(): List<HouseRemoteKeysDB>

    @Query("SELECT * FROM houseRemoteKeys WHERE houseId = :houseId")
    fun remoteKeysByHouseId(houseId: Long): HouseRemoteKeysDB?

    @Query("DELETE FROM houseRemoteKeys")
    suspend fun deleteAll()
}