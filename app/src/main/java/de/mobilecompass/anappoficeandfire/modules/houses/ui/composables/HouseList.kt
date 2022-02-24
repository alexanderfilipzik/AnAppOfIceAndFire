package de.mobilecompass.anappoficeandfire.modules.houses.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import de.mobilecompass.anappoficeandfire.core.ui.theme.AnAppOfIceAndFireTheme
import de.mobilecompass.anappoficeandfire.modules.houses.database.models.HouseDB
import de.mobilecompass.anappoficeandfire.modules.houses.database.models.asHouse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
fun HouseList(houses: Flow<PagingData<HouseDB>>) {
    val housesAsLazyPagingItems: LazyPagingItems<HouseDB> = houses.collectAsLazyPagingItems()

    Column {
        LazyColumn {
            items(items = housesAsLazyPagingItems) {
                it?.let {
                    HouseListEntry(house = it.asHouse())
                }
            }
        }
    }
}

/**
 * Somehow this is not working, see issue https://issuetracker.google.com/issues/194544557
 */
@Preview
@Composable
fun HouseListPreview() {
    AnAppOfIceAndFireTheme {
        HouseList(
            houses = flowOf(
                PagingData.from(
                    listOf(
                        HouseDB(1, "URL_1", "House Stark"),
                        HouseDB(2, "URL_2", "House Baratheon")
                    )
                )
            )
        )
    }
}