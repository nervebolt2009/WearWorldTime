package com.example.wearworldtime.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.*
import com.example.wearworldtime.ui.components.CityCard
import com.example.wearworldtime.ui.components.rotaryHandler
import com.example.wearworldtime.viewmodel.MainViewModel
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.IconButton

@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    onSearchClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val listState = rememberScalingLazyListState()

    Scaffold(
        timeText = { TimeText() },
        vignette = { Vignette(vignettePosition = VignettePosition.TopAndBottom) },
        positionIndicator = { PositionIndicator(scalingLazyListState = listState) }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .rotaryHandler(listState)
        ) {
            ScalingLazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = listState,
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (uiState.selectedCities.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillParentMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No cities added.",
                                style = MaterialTheme.typography.caption1,
                                color = MaterialTheme.colors.secondary
                            )
                        }
                    }
                } else {
                    items(uiState.selectedCities) { city ->
                        CityCard(
                            city = city,
                            onClick = { /* Detail view could go here */ },
                            onDelete = { viewModel.removeCity(city.id) }
                        )
                    }
                }
            }

            // Floating Action Button for Search
            FloatingActionButton(
                onClick = onSearchClick,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 12.dp)
            ) {
                Icon(
                    imageVector = androidx.compose.material.icons.Icons.Default.Search,
                    contentDescription = "Search"
                )
            }
        }
    }
}
