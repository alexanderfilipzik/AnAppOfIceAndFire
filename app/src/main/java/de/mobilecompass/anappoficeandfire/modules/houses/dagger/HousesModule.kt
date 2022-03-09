package de.mobilecompass.anappoficeandfire.modules.houses.dagger

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import de.mobilecompass.anappoficeandfire.modules.houses.HousesRepository
import de.mobilecompass.anappoficeandfire.modules.houses.HousesRepositoryImpl
import de.mobilecompass.anappoficeandfire.modules.houses.database.HouseDatabase
import de.mobilecompass.anappoficeandfire.modules.houses.database.HousesLocalDatasource
import de.mobilecompass.anappoficeandfire.modules.houses.database.HousesLocalDatasourceImpl
import de.mobilecompass.anappoficeandfire.modules.houses.network.HousesApi
import de.mobilecompass.anappoficeandfire.modules.houses.network.HousesRemoteDataSource
import de.mobilecompass.anappoficeandfire.modules.houses.network.HousesRemoteDataSourceImpl
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class HousesModule(private val context: Context) {

    companion object {
        val LOG_TAG: String = HousesModule::class.java.simpleName
    }

    @Provides
    @Singleton
    fun provideDatabase() = Room
        .databaseBuilder(
            context.applicationContext,
            HouseDatabase::class.java,
            "houseDatabase"
        )
        .build()

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): HousesApi = retrofit.create(HousesApi::class.java)

    @Provides
    @Singleton
    fun provideLocalDatasource(localDatasourceImpl: HousesLocalDatasourceImpl): HousesLocalDatasource =
        localDatasourceImpl

    @Provides
    @Singleton
    fun provideRemoteDatasource(remoteDataSourceImpl: HousesRemoteDataSourceImpl): HousesRemoteDataSource =
        remoteDataSourceImpl

    @Provides
    @Singleton
    fun provideRepository(housesRepository: HousesRepositoryImpl): HousesRepository =
        housesRepository
}