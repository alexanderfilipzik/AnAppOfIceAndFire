package de.mobilecompass.anappoficeandfire.core.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
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
            HouseList(viewModel.houses) { houseId ->
                navigateToHouseDetail(navController, houseId)
            }
        }

        val houseIdParameterName = "houseId"
        composable(
            route = "$housesRouteName/{$houseIdParameterName}",
            arguments = listOf(
                navArgument(houseIdParameterName) { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val houseId = backStackEntry.arguments?.getLong(houseIdParameterName)
            val viewModel = viewModel<HouseDetailViewModel>(initializer = {
                HouseDetailViewModel(houseId)
            })
            val state = viewModel.state
            HouseDetailForState(state)
        }
    }
}

private fun navigateToHouseDetail(navController: NavHostController, houseId: Long) {
    navController.navigate("${Houses.name}/$houseId")
}