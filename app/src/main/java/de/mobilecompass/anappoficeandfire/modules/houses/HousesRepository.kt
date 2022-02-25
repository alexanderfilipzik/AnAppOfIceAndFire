package de.mobilecompass.anappoficeandfire.modules.houses

import androidx.paging.Pager
import de.mobilecompass.anappoficeandfire.modules.houses.database.models.HouseDB
import de.mobilecompass.anappoficeandfire.modules.houses.domain.model.House

interface HousesRepository {

    // ----------------------------------------------------------------------------
    // region Properties
    // ----------------------------------------------------------------------------

    // ----------------------------------------------------------------------------
    // endregion
    // ----------------------------------------------------------------------------

    // ----------------------------------------------------------------------------
    // region Methods
    // ----------------------------------------------------------------------------

    fun pager(): Pager<Int, HouseDB>

    suspend fun getHouse(houseId: Long): House?

    // ----------------------------------------------------------------------------
    // endregion
    // ----------------------------------------------------------------------------
}