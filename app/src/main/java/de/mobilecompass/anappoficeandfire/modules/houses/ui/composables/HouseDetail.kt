package de.mobilecompass.anappoficeandfire.modules.houses.ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.mobilecompass.anappoficeandfire.core.ui.theme.AnAppOfIceAndFireTheme
import de.mobilecompass.anappoficeandfire.modules.houses.domain.model.House
import de.mobilecompass.anappoficeandfire.modules.houses.ui.viewmodels.HouseDetailViewModel.*
import de.mobilecompass.anappoficeandfire.modules.houses.ui.viewmodels.HouseDetailViewModel.State.*

@Composable
fun HouseDetailForState(state: State) {
    when(state) {
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
fun HouseDetail(house: House) {
    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 4.dp,
                vertical = 2.dp
            )
            .clip(RoundedCornerShape(4.dp)),
        elevation = 8.dp
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Row {
                Text(
                    text = house.name,
                    style = MaterialTheme.typography.h6
                )
            }
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Column {
                    Text(
                        text = house.region.ifEmpty { "-" },
                        style = MaterialTheme.typography.subtitle1,
                    )
                    Text(
                        text = house.words.ifEmpty { "-" },
                        style = MaterialTheme.typography.subtitle1,
                        fontStyle = FontStyle.Italic
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview_HouseDetail_Success() {
    AnAppOfIceAndFireTheme {
        HouseDetailForState(state = Success(House(
            name = "House Stark",
            words = "Winter is Coming",
            region = "The North"
        )))
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