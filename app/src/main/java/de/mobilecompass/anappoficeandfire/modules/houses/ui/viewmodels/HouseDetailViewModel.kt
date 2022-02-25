package de.mobilecompass.anappoficeandfire.modules.houses.ui.viewmodels

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import de.mobilecompass.anappoficeandfire.core.FireAndIceApplication
import de.mobilecompass.anappoficeandfire.modules.houses.HousesRepository
import de.mobilecompass.anappoficeandfire.modules.houses.domain.model.House
import javax.inject.Inject

class HouseDetailViewModel(val houseId: Long?): ViewModel() {

    // ----------------------------------------------------------------------------
    // region Inner types
    // ----------------------------------------------------------------------------

    sealed class State {
        object Loading: State()
        class Error(val message: String): State()
        class Success(val house: House): State()
    }

    // ----------------------------------------------------------------------------
    // endregion
    // ----------------------------------------------------------------------------


    companion object {
        // ----------------------------------------------------------------------------
        // region Constants
        // ----------------------------------------------------------------------------

        val LOG_TAG: String = HouseDetailViewModel::class.java.simpleName

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

    lateinit var state: LiveData<State>
        private set

    @Inject
    lateinit var repository: HousesRepository

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
        FireAndIceApplication.appComponent.inject(this)
        loadHouse()
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

    private fun loadHouse() {
        val houseId = houseId ?: run {
            state = MutableLiveData(State.Error("No ID for house given"))
            return
        }

        state = Transformations.map(repository.getHouse(houseId)) { house ->
            val house = house ?: run {
                return@map State.Error("No house for id $houseId available")
            }

            return@map State.Success(house)
        }
    }

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