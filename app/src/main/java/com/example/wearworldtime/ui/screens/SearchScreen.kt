package com.example.wearworldtime.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.*
import com.example.wearworldtime.data.City
import com.example.wearworldtime.ui.components.rotaryHandler
import com.example.wearworldtime.viewmodel.MainViewModel
import androidx.compose.material.IconButton

@OptIn(ExperimentalWearMaterialApi::class)
@Composable
fun SearchScreen(
    viewModel: MainViewModel,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val listState = rememberLazyListState()

    Scaffold(
        timeText = { TimeText() },
        vignette = { Vignette(vignettePosition = VignettePosition.TopAndBottom) },
        positionIndicator = { PositionIndicator(scalingLazyListState = listState) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .rotaryHandler(listState)
        ) {
            // Search bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // In a real app, this would trigger the system Wear OS keyboard
                // For this prototype, we'll use a simplified text input concept
                Text(
                    text = if (uiState.searchQuery.isEmpty()) "Search Cities..." else uiState.searchQuery,
                    style = MaterialTheme.typography.caption2,
                    color = if (uiState.searchQuery.isEmpty()) MaterialTheme.colors.secondary else MaterialTheme.colors.onSurface
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = androidx.compose.material.icons.Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }

            if (uiState.searchResults.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "No results",
                        style = MaterialTheme.typography.caption1
                    )
                }
            } else {
                LazyColumn(
                    state = listState,
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.searchResults) { city ->
                        CompactCityRow(
                            city = city,
                            onClick = {
                                viewModel.addCity(city.id)
                                viewModel.stopSearching()
                                onBack()
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CompactCityRow(city: City, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            Text(text = city.name, style = MaterialTheme.typography.body1)
            Text(text = city.country, style = MaterialTheme.typography.caption2)
        }
    }
}
