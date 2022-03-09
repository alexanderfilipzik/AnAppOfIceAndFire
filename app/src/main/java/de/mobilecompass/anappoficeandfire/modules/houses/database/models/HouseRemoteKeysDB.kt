package de.mobilecompass.anappoficeandfire.modules.houses.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "houseRemoteKeys")
data class HouseRemoteKeysDB(
    @PrimaryKey
    val houseId: Long,
    val previousUrl: String?,
    val nextUrl: String?
)