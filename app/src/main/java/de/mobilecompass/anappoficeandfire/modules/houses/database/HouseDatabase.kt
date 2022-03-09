package de.mobilecompass.anappoficeandfire.modules.houses.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import de.mobilecompass.anappoficeandfire.modules.houses.database.converter.HouseDBConverters
import de.mobilecompass.anappoficeandfire.modules.houses.database.models.HouseDB
import de.mobilecompass.anappoficeandfire.modules.houses.database.models.HouseRemoteKeysDB

@Database(entities = [HouseDB::class, HouseRemoteKeysDB::class], version = 1)
@TypeConverters(HouseDBConverters::class)
abstract class HouseDatabase: RoomDatabase() {

    companion object {
        val LOG_TAG: String = HouseDatabase::class.java.simpleName
    }

    abstract val houseDao: HouseDao
    abstract val houseRemoteKeysDao: HouseRemoteKeysDao
}