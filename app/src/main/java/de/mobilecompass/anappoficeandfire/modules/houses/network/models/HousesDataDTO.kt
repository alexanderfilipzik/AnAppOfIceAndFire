package de.mobilecompass.anappoficeandfire.modules.houses.network.models

import de.mobilecompass.anappoficeandfire.modules.houses.database.models.HouseRemoteKeysDB

data class HousesDataDTO(
    val list: List<HouseDTO>,
    val previousPageUrl: String?,
    val nextPageUrl: String?
)

fun HousesDataDTO.asRemoteKeysDB() = list.map {
    HouseRemoteKeysDB(
        it.asHouseDB().id,
        previousPageUrl,
        nextPageUrl
    )
}