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

    // ----------------------------------------------------------------------------
    // region Inner types
    // ----------------------------------------------------------------------------

    // ----------------------------------------------------------------------------
    // endregion
    // ----------------------------------------------------------------------------


    companion object {
        // ----------------------------------------------------------------------------
        // region Constants
        // ----------------------------------------------------------------------------

        val LOG_TAG: String = HousesModule::class.java.simpleName

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
    fun provideApi(retrofit: Retrofit) = retrofit.create(HousesApi::class.java)

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