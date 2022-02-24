package de.mobilecompass.anappoficeandfire.modules.houses.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import de.mobilecompass.anappoficeandfire.modules.houses.domain.model.House

@Entity(tableName = "houses")
data class HouseDB(

    @PrimaryKey
    val id: Long,

    val url: String = "",

    val name: String = "",
    val region: String = "",
    val coatOfArms: String = "",
    val words: String = "",
    val titles: List<String> = listOf(),
    val seats: List<String> = listOf(),
    val currentLord: String = "",
    val heir: String = "",
    val overlordUrl: String = "",
    val founded: String = "",
    val founderUrl: String = "",
    val diedOut: String = "",
    val ancestralWeapons: List<String> = listOf(),
    val cadetBranches: List<String> = listOf(),
    val swornMembersUrls: List<String> = listOf()
) {

    // ----------------------------------------------------------------------------
    // region Inner types
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

    // ----------------------------------------------------------------------------
    // endregion
    // ----------------------------------------------------------------------------

}

fun HouseDB.asHouse() = House(
    name = name,
    region = region,
    coatOfArms = coatOfArms,
    words = words,
    titles = titles,
    seats = seats,
    currentLord = currentLord,
    overlordUrl = overlordUrl,
    heir = heir,
    founded = founded,
    founderUrl = founderUrl,
    diedOut = diedOut,
    ancestralWeapons = ancestralWeapons,
    cadetBranches = cadetBranches,
    swornMembersUrls = swornMembersUrls
)