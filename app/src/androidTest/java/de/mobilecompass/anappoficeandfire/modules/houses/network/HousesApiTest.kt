package de.mobilecompass.anappoficeandfire.modules.houses.network

import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@RunWith(AndroidJUnit4::class)
class HousesApiTest {

    companion object {
        val LOG_TAG: String = HousesApiTest::class.java.simpleName
    }

    private lateinit var api: HousesApi

    @Before
    fun initApi() {
        if (this::api.isInitialized)
            return

        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.anapioficeandfire.com/api/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        api = retrofit.create(HousesApi::class.java)
    }

    @Test
    fun testE2E_apiHouses() = runTest {
        val pageSize = 20
        val response = api.getHousesByURL("https://www.anapioficeandfire.com/api/houses?pageSize=$pageSize")

        Assert.assertTrue(response.isSuccessful)

        val housesList = response.body()

        Assert.assertNotNull(housesList)
        Assert.assertEquals(pageSize, housesList?.size)
    }

}