package de.mobilecompass.anappoficeandfire.modules.houses.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import de.mobilecompass.anappoficeandfire.R
import de.mobilecompass.anappoficeandfire.modules.houses.database.models.HouseDB
import de.mobilecompass.anappoficeandfire.modules.houses.database.models.asHouse
import kotlinx.coroutines.flow.Flow

@Composable
fun HouseList(houses: Flow<PagingData<HouseDB>>, onHouseClicked: (Long) -> Unit) {
    val housesAsLazyPagingItems: LazyPagingItems<HouseDB> = houses.collectAsLazyPagingItems()
    val isRefreshing =
        housesAsLazyPagingItems.loadState.refresh == LoadState.Loading || housesAsLazyPagingItems.loadState.append == LoadState.Loading

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

    val error = housesAsLazyPagingItems.loadState.refresh as? LoadState.Error ?:
    housesAsLazyPagingItems.loadState.append as? LoadState.Error ?:
    housesAsLazyPagingItems.loadState.prepend as? LoadState.Error

    error?.let { error ->
        Box(modifier = Modifier.fillMaxHeight(), contentAlignment = Alignment.BottomCenter) {
            Snackbar(action = {
                Button(onClick = {
                    housesAsLazyPagingItems.retry()
                }) {
                    Text(stringResource(R.string.button_label_retry_on_fetch_error))
                }
            }) {
                Text(text = error.error.toString())
            }
        }
    }
}
