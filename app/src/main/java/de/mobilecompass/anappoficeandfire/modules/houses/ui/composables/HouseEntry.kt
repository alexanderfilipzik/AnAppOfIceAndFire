package de.mobilecompass.anappoficeandfire.modules.houses.ui.composables

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import de.mobilecompass.anappoficeandfire.core.ui.theme.AnAppOfIceAndFireTheme
import de.mobilecompass.anappoficeandfire.modules.houses.domain.model.House

@Composable
fun HouseListEntry(house: House) {
    Text(house.name)
}

@Preview
@Composable
fun SingleEntryPreview() {
    AnAppOfIceAndFireTheme {
        HouseListEntry(
            House("House Stark")
        )
    }
}