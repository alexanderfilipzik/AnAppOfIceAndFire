package de.mobilecompass.anappoficeandfire.modules.houses.ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.mobilecompass.anappoficeandfire.R
import de.mobilecompass.anappoficeandfire.core.ui.theme.AnAppOfIceAndFireTheme
import de.mobilecompass.anappoficeandfire.modules.houses.domain.model.House
import de.mobilecompass.anappoficeandfire.modules.houses.ui.viewmodels.HouseDetailViewModel.*
import de.mobilecompass.anappoficeandfire.modules.houses.ui.viewmodels.HouseDetailViewModel.State.*

@Composable
fun HouseDetailForState(state: State) {
    when (state) {
        is Loading -> HouseDetailLoading()
        is Error -> HouseDetailError(state)
        is Success -> HouseDetail(state.house)
    }
}

@Composable
fun FullscreenCenteredContent(scope: @Composable () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
    ) {
        scope()
    }
}

@Composable
fun HouseDetailLoading() {
    FullscreenCenteredContent {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator()
            Text(
                "Enter house...",
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun HouseDetailError(error: Error) {
    FullscreenCenteredContent {
        Text(
            error.message,
            style = MaterialTheme.typography.body1
        )
    }
}

@Composable
fun HouseDescriptor(
    label: String,
    text: String
) {
    if (text.isBlank())
        return

    HouseDescriptor(label = label, textEntries = listOf(text))
}

@Composable
fun HouseDescriptor(
    label: String,
    textEntries: List<String>
) {
    if (textEntries.isEmpty())
        return

    if (textEntries.all { it.isBlank() })
        return

    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.h5
        )

        textEntries.map { text ->
            Text(
                text = text.ifBlank { "-" },
                style = MaterialTheme.typography.body1
            )
        }
    }
}

@Composable
fun HouseDetail(house: House) {
    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {

        Column(
            modifier = Modifier
                .padding(8.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = house.name,
                style = MaterialTheme.typography.h4
            )
            HouseDescriptor(
                label = stringResource(R.string.label_house_region),
                text = house.region,
            )
            HouseDescriptor(
                label = stringResource(R.string.label_house_coat_of_current_founded),
                text = house.founded,
            )
            HouseDescriptor(
                label = stringResource(R.string.label_house_coat_of_current_died_out),
                text = house.diedOut,
            )
            HouseDescriptor(
                label = stringResource(R.string.label_house_words),
                text = house.words
            )
            HouseDescriptor(
                label = stringResource(R.string.label_house_coat_of_arms),
                text = house.coatOfArms
            )
            HouseDescriptor(
                label = stringResource(R.string.label_house_coat_of_titles),
                textEntries = house.titles
            )
            HouseDescriptor(
                label = stringResource(R.string.label_house_coat_of_seats),
                textEntries = house.seats
            )
            HouseDescriptor(
                label = stringResource(R.string.label_house_coat_of_ancestral_weapons),
                textEntries = house.ancestralWeapons
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview_HouseDetail_Success() {
    AnAppOfIceAndFireTheme {
        HouseDetailForState(
            state = Success(
                House(
                    name = "House Stark",
                    words = "Winter is Coming",
                    region = "The North"
                )
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Preview_HouseDetail_Error() {
    AnAppOfIceAndFireTheme {
        HouseDetailForState(state = Error("Some error occured"))
    }
}

@Preview(showBackground = true)
@Composable
fun Preview_HouseDetail_Loading() {
    AnAppOfIceAndFireTheme {
        HouseDetailForState(state = Loading)
    }
}