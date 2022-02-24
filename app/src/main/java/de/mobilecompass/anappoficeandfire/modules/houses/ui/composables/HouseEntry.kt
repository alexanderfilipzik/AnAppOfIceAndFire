package de.mobilecompass.anappoficeandfire.modules.houses.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.mobilecompass.anappoficeandfire.core.ui.theme.AnAppOfIceAndFireTheme
import de.mobilecompass.anappoficeandfire.modules.houses.domain.model.House

@Composable
fun HouseListEntry(house: House) {
    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier
            .clickable { }
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
fun SingleEntryPreview() {
    AnAppOfIceAndFireTheme {
        HouseListEntry(
            House(
                name = "House Stark",
                words = "Winter is Coming",
                region = "The North"
            )
        )
    }
}