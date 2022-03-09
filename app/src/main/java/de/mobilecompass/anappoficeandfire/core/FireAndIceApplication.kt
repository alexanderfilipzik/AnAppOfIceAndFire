package de.mobilecompass.anappoficeandfire.core

import android.app.Application
import de.mobilecompass.anappoficeandfire.core.dagger.AppComponent
import de.mobilecompass.anappoficeandfire.core.dagger.DaggerAppComponent
import de.mobilecompass.anappoficeandfire.core.dagger.NetworkModule
import de.mobilecompass.anappoficeandfire.modules.houses.dagger.HousesModule
import timber.log.Timber

class FireAndIceApplication: Application() {

    companion object {

        val LOG_TAG: String = FireAndIceApplication::class.java.simpleName

        lateinit var appComponent: AppComponent
            private set
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        initDagger()
    }

    private fun initDagger() {
        appComponent = DaggerAppComponent.builder()
            // currently it doesn't make a difference if we specify a value here, because we are doing direct api calls
            .networkModule(NetworkModule("https://www.anapioficeandfire.com/api/"))
            .housesModule(HousesModule(this))
            .build()
    }
}