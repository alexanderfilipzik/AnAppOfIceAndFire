package de.mobilecompass.anappoficeandfire.modules.houses.network

import de.mobilecompass.anappoficeandfire.modules.houses.network.models.HouseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Url

interface HousesApi {
    @GET
    @Headers(
        "Accept: application/vnd.anapioficeandfire+json; version=1"
    )
    suspend fun getHousesByURL(@Url url: String): Response<List<HouseDTO>>
}