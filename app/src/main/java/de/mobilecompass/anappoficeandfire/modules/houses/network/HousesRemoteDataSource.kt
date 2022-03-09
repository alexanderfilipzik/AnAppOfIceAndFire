package de.mobilecompass.anappoficeandfire.modules.houses.network

import de.mobilecompass.anappoficeandfire.modules.houses.network.models.HousesDataDTO

interface HousesRemoteDataSource {
    suspend fun getHouses(url: String): Result<HousesDataDTO>
}