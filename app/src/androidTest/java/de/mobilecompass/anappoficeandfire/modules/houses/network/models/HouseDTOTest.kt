package de.mobilecompass.anappoficeandfire.modules.houses.network.models

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HouseDTOTest {

    companion object {
        val LOG_TAG: String = HouseDTOTest::class.java.simpleName
    }

    @Test
    fun correctHouseDtoToDatabaseConversion() {
        val id = 1234L
        val name = "House"
        val dtoModel = HouseDTO("https://www.anapioficeandfire.com/api/houses/$id", name)

        val dbModel = dtoModel.asHouseDB()

        Assert.assertEquals(dtoModel.url, dbModel.url)
        Assert.assertEquals(id, dbModel.id)
        Assert.assertEquals(name, dbModel.name)
    }

    @Test
    fun correctHouseDataDtoToRemoteKeysDatabaseConversion() {
        val id = 1234L

        val dtoModel = HousesDataDTO(
            listOf(HouseDTO("https://www.anapioficeandfire.com/api/houses/$id", "House $id")),
            null,
            "next/page"
        )

        val dbModel = dtoModel.asRemoteKeysDB()

        Assert.assertTrue(dbModel.isNotEmpty())
        dbModel.forEach {
            Assert.assertEquals(id, it.houseId)
            Assert.assertEquals(dtoModel.previousPageUrl, it.previousUrl)
            Assert.assertEquals(dtoModel.nextPageUrl, it.nextUrl)
        }
    }

}