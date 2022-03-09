package de.mobilecompass.anappoficeandfire.modules.houses.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import de.mobilecompass.anappoficeandfire.core.FireAndIceApplication
import de.mobilecompass.anappoficeandfire.modules.houses.HousesRepository
import de.mobilecompass.anappoficeandfire.modules.houses.database.models.HouseDB
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HouseListViewModel: ViewModel() {

    companion object {
        val LOG_TAG: String = HouseListViewModel::class.java.simpleName
    }

    @Inject
    lateinit var repository: HousesRepository

    @OptIn(ExperimentalPagingApi::class)
    val houses: Flow<PagingData<HouseDB>>

    init {
        FireAndIceApplication.appComponent.inject(this)
        houses = repository.pager().flow.cachedIn(viewModelScope)
    }
}