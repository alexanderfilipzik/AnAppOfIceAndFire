package de.mobilecompass.anappoficeandfire.core.dagger

import dagger.Component
import de.mobilecompass.anappoficeandfire.modules.houses.dagger.HousesModule
import de.mobilecompass.anappoficeandfire.modules.houses.ui.viewmodels.HouseDetailViewModel
import de.mobilecompass.anappoficeandfire.modules.houses.ui.viewmodels.HouseListViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [
    NetworkModule::class,
    HousesModule::class
])
interface AppComponent {
    fun inject(houseListViewModel: HouseListViewModel)
    fun inject(houseDetailViewModel: HouseDetailViewModel)
}