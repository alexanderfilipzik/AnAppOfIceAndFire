package de.mobilecompass.anappoficeandfire.modules.houses.network.models

data class HousesData(
    val list: List<HouseDTO>,
    val previousPageUrl: String?,
    val nextPageUrl: String?
)
