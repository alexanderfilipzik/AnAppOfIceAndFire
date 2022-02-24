package de.mobilecompass.anappoficeandfire

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import de.mobilecompass.anappoficeandfire.core.ui.theme.AnAppOfIceAndFireTheme
import de.mobilecompass.anappoficeandfire.modules.houses.ui.composables.HouseList
import de.mobilecompass.anappoficeandfire.modules.houses.ui.viewmodels.HouseListViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: HouseListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnAppOfIceAndFireTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    HouseList(houses = viewModel.houses)
                }
            }
        }
    }
}