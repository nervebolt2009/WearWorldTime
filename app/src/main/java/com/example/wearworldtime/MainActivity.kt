package com.example.wearworldtime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wearworldtime.ui.screens.HomeScreen
import com.example.wearworldtime.ui.screens.SearchScreen
import com.example.wearworldtime.ui.theme.WearWorldTimeTheme
import com.example.wearworldtime.viewmodel.MainViewModel

enum class Screen {
    Home,
    Search
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WearWorldTimeTheme {
                val viewModel: MainViewModel = viewModel()
                var currentScreen by remember { mutableStateOf(Screen.Home) }

                when (currentScreen) {
                    Screen.Home -> HomeScreen(
                        viewModel = viewModel,
                        onSearchClick = { currentScreen = Screen.Search }
                    )
                    Screen.Search -> SearchScreen(
                        viewModel = viewModel,
                        onBack = { currentScreen = Screen.Home }
                    )
                }
            }
        }
    }
}
