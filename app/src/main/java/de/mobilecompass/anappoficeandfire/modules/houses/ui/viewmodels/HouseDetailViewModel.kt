package de.mobilecompass.anappoficeandfire.modules.houses.ui.viewmodels

import androidx.lifecycle.*
import de.mobilecompass.anappoficeandfire.core.FireAndIceApplication
import de.mobilecompass.anappoficeandfire.modules.houses.HousesRepository
import de.mobilecompass.anappoficeandfire.modules.houses.domain.model.House
import javax.inject.Inject

class HouseDetailViewModel(val houseUrl: String?) : ViewModel() {

    sealed class State {
        object Loading : State()
        class Error(val message: String) : State()
        class Success(val house: House) : State()
    }

    companion object {
        val LOG_TAG: String = HouseDetailViewModel::class.java.simpleName
    }

    lateinit var state: LiveData<State>
        private set

    private lateinit var house: LiveData<House?>

    private lateinit var overlordHouse: LiveData<House?>
        private set

    @Inject
    lateinit var repository: HousesRepository

    init {
        FireAndIceApplication.appComponent.inject(this)
        loadHouse()
    }

    private fun loadHouse() {
        val houseUrl = houseUrl ?: run {
            state = MutableLiveData(State.Error("No url for house provided"))
            return
        }

        state = Transformations.map(repository.getHouse(houseUrl)) { house ->
            val house = house ?: run {
                return@map State.Error("No house for url $houseUrl available")
            }

            return@map State.Success(house)
        }
    }

}