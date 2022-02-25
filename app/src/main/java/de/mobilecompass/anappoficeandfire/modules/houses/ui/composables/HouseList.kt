package de.mobilecompass.anappoficeandfire.modules.houses.ui.composables

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import de.mobilecompass.anappoficeandfire.modules.houses.database.models.HouseDB
import de.mobilecompass.anappoficeandfire.modules.houses.database.models.asHouse
import kotlinx.coroutines.flow.Flow

@Composable
fun HouseList(houses: Flow<PagingData<HouseDB>>, onHouseClicked: (Long) -> Unit) {
    val housesAsLazyPagingItems: LazyPagingItems<HouseDB> = houses.collectAsLazyPagingItems()
    val isRefreshing =
        housesAsLazyPagingItems.loadState.refresh == LoadState.Loading || housesAsLazyPagingItems.loadState.append == LoadState.Loading
    // TODO: handle error state
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
        onRefresh = {
            housesAsLazyPagingItems.refresh()
        }
    ) {
        LazyColumn {
            items(items = housesAsLazyPagingItems) {
                it?.let {
                    HouseListEntry(house = it.asHouse()) {
                        onHouseClicked(it.id)
                    }
                }
            }
        }
    }
}
