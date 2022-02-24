package de.mobilecompass.anappoficeandfire.modules.houses.network

import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.Component
import dagger.Module
import dagger.Provides
import de.mobilecompass.anappoficeandfire.modules.houses.network.models.HouseDTO
import kotlinx.coroutines.test.runTest
import okhttp3.Headers
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton


@RunWith(AndroidJUnit4::class)
class HousesRemoteDataSourceTest {

    // ----------------------------------------------------------------------------
    // region Inner types
    // ----------------------------------------------------------------------------

    @Singleton
    @Component(modules = [DaggerModule::class])
    interface TestComponent {
        fun inject(test: HousesRemoteDataSourceTest)
    }

    @Module
    class DaggerModule {
        @Provides
        @Singleton
        fun provideHousesApi(): HousesApi = HousesApiMock()

        @Provides
        @Singleton
        fun provideHousesRemoteDataSource(dataSource: HousesRemoteDataSourceImpl): HousesRemoteDataSource =
            dataSource
    }

    class HousesApiMock : HousesApi {
        override suspend fun getHousesByURL(url: String): Response<List<HouseDTO>> {
            return Response.success(
                listOf(HouseDTO("")),
                Headers.of(
                    mapOf(
                        "link" to "<$nextUrl>; rel=\"next\", " +
                                "<$previousUrl>; rel=\"prev\", " +
                                "<https://www.anapioficeandfire.com/api/characters?page=1&pageSize=10>; rel=\"first\", " +
                                "<https://www.anapioficeandfire.com/api/characters?page=214&pageSize=10>; rel=\"last\""
                    )
                )
            )
        }
    }

    // ----------------------------------------------------------------------------
    // endregion
    // ----------------------------------------------------------------------------


    companion object {
        // ----------------------------------------------------------------------------
        // region Constants
        // ----------------------------------------------------------------------------

        val LOG_TAG: String = HousesRemoteDataSourceTest::class.java.simpleName

        val nextUrl = "https://www.anapioficeandfire.com/api/characters?page=3&pageSize=10"
        val previousUrl = "https://www.anapioficeandfire.com/api/characters?page=1&pageSize=10"
        // ----------------------------------------------------------------------------
        // endregion
        // ----------------------------------------------------------------------------

        // ----------------------------------------------------------------------------
        // region Convenience methods
        // ----------------------------------------------------------------------------

        // ----------------------------------------------------------------------------
        // endregion
        // ----------------------------------------------------------------------------
    }

    // ----------------------------------------------------------------------------
    // region Public properties
    // ----------------------------------------------------------------------------

    @Inject
    lateinit var housesRemoteDataSource: HousesRemoteDataSource

    // ----------------------------------------------------------------------------
    // endregion
    // ----------------------------------------------------------------------------

    // ----------------------------------------------------------------------------
    // region Internal properties
    // ----------------------------------------------------------------------------

    // ----------------------------------------------------------------------------
    // endregion
    // ----------------------------------------------------------------------------

    // ----------------------------------------------------------------------------
    // region Protected properties
    // ----------------------------------------------------------------------------

    // ----------------------------------------------------------------------------
    // endregion
    // ----------------------------------------------------------------------------

    // ----------------------------------------------------------------------------
    // region Private properties
    // ----------------------------------------------------------------------------

    // ----------------------------------------------------------------------------
    // endregion
    // ----------------------------------------------------------------------------

    // ----------------------------------------------------------------------------
    // region Init
    // ----------------------------------------------------------------------------

    init {
        val component = DaggerHousesRemoteDataSourceTest_TestComponent
            .builder()
            .daggerModule(DaggerModule())
            .build()
        component.inject(this)
    }

    // ----------------------------------------------------------------------------
    // endregion
    // ----------------------------------------------------------------------------

    // ----------------------------------------------------------------------------
    // region System/Overridden methods
    // ----------------------------------------------------------------------------

    // ----------------------------------------------------------------------------
    // endregion
    // ----------------------------------------------------------------------------

    // ----------------------------------------------------------------------------
    // region Public methods
    // ----------------------------------------------------------------------------

    @Test
    fun testDataSource() = runTest {
        val result = housesRemoteDataSource.getHouses("")

        Assert.assertTrue(result.isSuccess)

        val housesData = result.getOrNull()

        Assert.assertNotNull(housesData)
        Assert.assertEquals(previousUrl, housesData?.previousPageUrl)
        Assert.assertEquals(nextUrl, housesData?.nextPageUrl)
    }

    // ----------------------------------------------------------------------------
    // endregion
    // ----------------------------------------------------------------------------

    // ----------------------------------------------------------------------------
    // region Protected methods
    // ----------------------------------------------------------------------------

    // ----------------------------------------------------------------------------
    // endregion
    // ----------------------------------------------------------------------------

    // ----------------------------------------------------------------------------
    // region Private methods
    // ----------------------------------------------------------------------------

    // ----------------------------------------------------------------------------
    // endregion
    // ----------------------------------------------------------------------------

    // ----------------------------------------------------------------------------
    // region Interfaces/Listener
    // ----------------------------------------------------------------------------

    // ----------------------------------------------------------------------------
    // endregion
    // ----------------------------------------------------------------------------

}