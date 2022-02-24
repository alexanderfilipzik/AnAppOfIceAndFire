package de.mobilecompass.anappoficeandfire.modules.houses.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import de.mobilecompass.anappoficeandfire.modules.houses.database.models.HouseDB
import de.mobilecompass.anappoficeandfire.modules.houses.database.models.asHouse
import de.mobilecompass.anappoficeandfire.modules.houses.ui.viewmodels.HouseListViewModel

@Composable
fun HouseList(viewModel: HouseListViewModel) {
    val housesAsLazyPagingItems: LazyPagingItems<HouseDB> = viewModel.houses.collectAsLazyPagingItems()
    val isRefreshing = housesAsLazyPagingItems.loadState.refresh == LoadState.Loading
    SwipeRefresh(state = rememberSwipeRefreshState(isRefreshing = isRefreshing), onRefresh = { housesAsLazyPagingItems.refresh() }) {
        LazyColumn {
            items(items = housesAsLazyPagingItems) {
                it?.let {
                    HouseListEntry(house = it.asHouse())
                }
            }
        }
    }
}
