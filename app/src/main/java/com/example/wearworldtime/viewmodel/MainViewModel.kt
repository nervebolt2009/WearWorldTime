package com.example.wearworldtime.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.wearworldtime.data.City
import com.example.wearworldtime.data.TimeZoneRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class MainUiState(
    val selectedCities: List<City> = emptyList(),
    val searchResults: List<City> = emptyList(),
    val searchQuery: String = "",
    val isSearching: Boolean = false
)

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = TimeZoneRepository(application)

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    init {
        refreshSelectedCities()
    }

    fun refreshSelectedCities() {
        _uiState.value = _uiState.value.copy(
            selectedCities = repository.getSelectedCities()
        )
    }

    fun onSearchQueryChanged(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
        if (query.isEmpty()) {
            _uiState.value = _uiState.value.copy(searchResults = emptyList())
        } else {
            val results = repository.searchCities(query)
            _uiState.value = _uiState.value.copy(searchResults = results)
        }
    }

    fun addCity(cityId: String) {
        if (repository.addCity(cityId)) {
            refreshSelectedCities()
            _uiState.value = _uiState.value.copy(searchQuery = "", searchResults = emptyList())
        }
    }

    fun removeCity(cityId: String) {
        if (repository.removeCity(cityId)) {
            refreshSelectedCities()
        }
    }

    fun startSearching() {
        _uiState.value = _uiState.value.copy(isSearching = true)
    }

    fun stopSearching() {
        _uiState.value = _uiState.value.copy(isSearching = false, searchQuery = "", searchResults = emptyList())
    }
}
