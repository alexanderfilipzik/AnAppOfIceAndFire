package de.mobilecompass.anappoficeandfire.core

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import de.mobilecompass.anappoficeandfire.core.ui.FireAndIceApp

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FireAndIceApp()
        }
    }
}