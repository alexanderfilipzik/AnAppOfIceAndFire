package de.mobilecompass.anappoficeandfire.modules.houses.domain

import androidx.paging.*
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.Component
import dagger.Module
import dagger.Provides
import de.mobilecompass.anappoficeandfire.modules.houses.database.HouseDatabase
import de.mobilecompass.anappoficeandfire.modules.houses.database.HousesLocalDatasource
import de.mobilecompass.anappoficeandfire.modules.houses.database.HousesLocalDatasourceImpl
import de.mobilecompass.anappoficeandfire.modules.houses.database.models.HouseDB
import de.mobilecompass.anappoficeandfire.modules.houses.network.HousesRemoteDataSource
import de.mobilecompass.anappoficeandfire.modules.houses.network.models.HouseDTO
import de.mobilecompass.anappoficeandfire.modules.houses.network.models.HousesDataDTO
import de.mobilecompass.anappoficeandfire.modules.houses.network.models.HousesRemoteException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@RunWith(AndroidJUnit4::class)
class HousesRemoteMediatorTest {
    @Singleton
    @Component(modules = [DaggerModule::class])
    interface TestComponent {
        fun inject(test: HousesRemoteMediatorTest)
    }

    @Module
    class DaggerModule {
        @Provides
        @Singleton
        fun provideDatabase(): HouseDatabase =
            Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                HouseDatabase::class.java
            ).build()

        @Provides
        @Singleton
        fun provideHousesRemoteDataSource() = HousesRemoteDataSourceMock()

        @Provides
        @Singleton
        fun provideHousesLocalDatasource(database: HouseDatabase): HousesLocalDatasource =
            HousesLocalDatasourceImpl(database)
    }

    class HousesRemoteDataSourceMock : HousesRemoteDataSource {

        var data: HousesDataDTO? = null
        var errorMessage: String? = null

        override suspend fun getHouses(url: String): Result<HousesDataDTO> =
            errorMessage?.let {
                Result.failure(HousesRemoteException(it))
            } ?: data?.let {
                Result.success(it)
            } ?: Result.success(
                HousesDataDTO(
                    listOf(),
                    null,
                    null
                )
            )
    }
    companion object {
        val LOG_TAG: String = HousesRemoteMediatorTest::class.java.simpleName
    }

    @Inject
    lateinit var housesRemoteDataSourceMock: HousesRemoteDataSourceMock

    @Inject
    lateinit var housesLocalDatasourceMock: HousesLocalDatasource

    @Inject
    lateinit var databaseMock: HouseDatabase

    init {
        val component = DaggerHousesRemoteMediatorTest_TestComponent
            .builder()
            .daggerModule(DaggerModule())
            .build()
        component.inject(this)
    }

    @After
    fun tearDown() = runTest {
        housesLocalDatasourceMock.deleteAll()
        housesRemoteDataSourceMock.data = null
    }

    @Test
    fun refreshLoadReturnsSuccessResultWhenMoreDataIsPresent() = runTest {
        housesRemoteDataSourceMock.data = HousesDataDTO(
            (0 until 10).map {
                HouseDTO("url/to/house/$it", "House $it")
            },
            null,
            "some/url/to/next/houses"
        )

        val remoteMediator = HousesRemoteMediator(
            "not/relevant/here",
            housesLocalDatasourceMock,
            housesRemoteDataSourceMock
        )

        val pagingState = PagingState<Int, HouseDB>(
            listOf(),
            null,
            PagingConfig(10),
            10
        )

        val result = remoteMediator.load(LoadType.REFRESH, pagingState)

        val housesInDb = databaseMock.houseDao.getAll()
        val remoteKeysInDb = databaseMock.houseRemoteKeysDao.getAll()

        Assert.assertFalse(housesInDb.isEmpty())
        Assert.assertFalse(remoteKeysInDb.isEmpty())

        val sizeOfMock = housesRemoteDataSourceMock.data?.list?.size

        Assert.assertEquals(sizeOfMock, housesInDb.size)
        Assert.assertEquals(sizeOfMock, remoteKeysInDb.size)

        Assert.assertTrue(result is RemoteMediator.MediatorResult.Success)
        Assert.assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @Test
    fun appendLoadSuccess() = runTest {
        housesRemoteDataSourceMock.data = HousesDataDTO(
            (0 until 10).map {
                HouseDTO("url/to/house/$it","House $it")
            },
            null,
            "some/url/to/next/houses"
        )

        val remoteMediator = HousesRemoteMediator(
            "not/relevant/here",
            housesLocalDatasourceMock,
            housesRemoteDataSourceMock
        )

        var pagingState = PagingState<Int, HouseDB>(
            listOf(),
            null,
            PagingConfig(10),
            10
        )

        remoteMediator.load(LoadType.REFRESH, pagingState)
        var housesInDb = databaseMock.houseDao.getAll()
        var remoteKeysInDb = databaseMock.houseRemoteKeysDao.getAll()

        Assert.assertEquals(10, housesInDb.size)
        Assert.assertEquals(10, remoteKeysInDb.size)

        pagingState = PagingState(
            listOf(
                PagingSource.LoadResult.Page(
                    housesInDb,
                    0,
                    1
                )
            ),
            null,
            PagingConfig(10),
            10
        )

        housesRemoteDataSourceMock.data = HousesDataDTO(
            (10 until 20).map {
                HouseDTO("url/to/house/$it", "House $it")
            },
            null,
            null
        )

        val result = remoteMediator.load(LoadType.APPEND, pagingState)

        housesInDb = databaseMock.houseDao.getAll()
        remoteKeysInDb = databaseMock.houseRemoteKeysDao.getAll()

        Assert.assertFalse(housesInDb.isEmpty())
        Assert.assertFalse(remoteKeysInDb.isEmpty())

        Assert.assertEquals(20, housesInDb.size)
        Assert.assertEquals(20, remoteKeysInDb.size)

        Assert.assertTrue(result is RemoteMediator.MediatorResult.Success)
        Assert.assertTrue((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @Test
    fun refreshLoadSuccessAndEndOfPaginationWhenNoMoreData() = runTest {

        val remoteMediator = HousesRemoteMediator(
            "not/relevant/here",
            housesLocalDatasourceMock,
            housesRemoteDataSourceMock
        )

        val pagingState = PagingState<Int, HouseDB>(
            listOf(),
            null,
            PagingConfig(10),
            10
        )

        val result = remoteMediator.load(LoadType.REFRESH, pagingState)

        val housesInDb = databaseMock.houseDao.getAll()
        val remoteKeysInDb = databaseMock.houseRemoteKeysDao.getAll()

        Assert.assertTrue(housesInDb.isEmpty())
        Assert.assertTrue(remoteKeysInDb.isEmpty())

        Assert.assertTrue(result is RemoteMediator.MediatorResult.Success)
        Assert.assertTrue((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @Test
    fun refreshLoadReturnsErrorResultWhenErrorOccurs() = runTest {

        housesRemoteDataSourceMock.errorMessage = "Some Error occured"

        val remoteMediator = HousesRemoteMediator(
            "not/relevant/here",
            housesLocalDatasourceMock,
            housesRemoteDataSourceMock
        )

        val pagingState = PagingState<Int, HouseDB>(
            listOf(),
            null,
            PagingConfig(10),
            10
        )

        val result = remoteMediator.load(LoadType.REFRESH, pagingState)

        val housesInDb = databaseMock.houseDao.getAll()
        val remoteKeysInDb = databaseMock.houseRemoteKeysDao.getAll()

        Assert.assertTrue(housesInDb.isEmpty())
        Assert.assertTrue(remoteKeysInDb.isEmpty())

        Assert.assertTrue(result is RemoteMediator.MediatorResult.Error)
        Assert.assertEquals(
            housesRemoteDataSourceMock.errorMessage,
            (result as RemoteMediator.MediatorResult.Error).throwable.message
        )
    }
}