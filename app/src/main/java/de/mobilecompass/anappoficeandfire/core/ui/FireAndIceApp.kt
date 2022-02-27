package de.mobilecompass.anappoficeandfire.core.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.compose.rememberNavController
import de.mobilecompass.anappoficeandfire.core.ui.theme.AnAppOfIceAndFireTheme
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import de.mobilecompass.anappoficeandfire.core.ui.FireAndIceScreen.Houses
import de.mobilecompass.anappoficeandfire.modules.houses.ui.composables.HouseDetailForState
import de.mobilecompass.anappoficeandfire.modules.houses.ui.composables.HouseList
import de.mobilecompass.anappoficeandfire.modules.houses.ui.viewmodels.HouseDetailViewModel
import de.mobilecompass.anappoficeandfire.modules.houses.ui.viewmodels.HouseListViewModel
import java.net.URLDecoder
import java.net.URLEncoder

@Composable
fun FireAndIceApp() {
    AnAppOfIceAndFireTheme() {
        val navController = rememberNavController()
        Scaffold(
        ) { innerPadding ->
            FireAndIceNavHost(navController, modifier = Modifier.padding(innerPadding))
        }
    }
}

@Composable
fun FireAndIceNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Houses.name,
        modifier = modifier
    ) {
        val housesRouteName = Houses.name

        composable(housesRouteName) {
            val viewModel = viewModel<HouseListViewModel>()
            HouseList(viewModel.houses) { houseUrl ->
                navigateToHouseDetail(navController, houseUrl)
            }
        }

        val houseUrlParameterName = "houseUrl"
        composable(
            route = "$housesRouteName/{$houseUrlParameterName}",
            arguments = listOf(
                navArgument(houseUrlParameterName) { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val houseUrlEncoded = backStackEntry.arguments?.getString(houseUrlParameterName)
            val houseUrl = URLDecoder.decode(houseUrlEncoded, "utf-8")
            val viewModel = viewModel<HouseDetailViewModel>(initializer = {
                HouseDetailViewModel(houseUrl)
            })
            val state: HouseDetailViewModel.State by viewModel.state.observeAsState(HouseDetailViewModel.State.Loading)
            HouseDetailForState(state)
        }
    }
}

private fun navigateToHouseDetail(navController: NavHostController, houseUrl: String) {
    val urlEncoded = URLEncoder.encode(houseUrl, "utf-8")
    navController.navigate("${Houses.name}/$urlEncoded")
}