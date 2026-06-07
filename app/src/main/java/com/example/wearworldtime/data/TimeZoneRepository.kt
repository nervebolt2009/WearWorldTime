package com.example.wearworldtime.data

import android.content.Context
import android.content.SharedPreferences

class TimeZoneRepository(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("world_time_prefs", Context.MODE_PRIVATE)

    companion object {
        val ALL_CITIES = listOf(
            City("london", "London", "United Kingdom", "Europe/London", "LDN"),
            City("paris", "Paris", "France", "Europe/Paris", "PAR"),
            City("cairo", "Cairo", "Egypt", "Africa/Cairo", "CAI"),
            City("dubai", "Dubai", "UAE", "Asia/Dubai", "DXB"),
            City("delhi", "New Delhi", "India", "Asia/Kolkata", "DEL"),
            City("bangkok", "Bangkok", "Thailand", "Asia/Bangkok", "BKK"),
            City("singapore", "Singapore", "Singapore", "Asia/Singapore", "SGP"),
            City("hong_kong", "Hong Kong", "China", "Asia/Hong_Kong", "HKG"),
            City("tokyo", "Tokyo", "Japan", "Asia/Tokyo", "TYO"),
            City("seoul", "Seoul", "South Korea", "Asia/Seoul", "SEL"),
            City("sydney", "Sydney", "Australia", "Australia/Sydney", "SYD"),
            City("auckland", "Auckland", "New Zealand", "Pacific/Auckland", "AKL"),
            City("honolulu", "Honolulu", "USA", "Pacific/Honolulu", "HNL"),
            City("anchorage", "Anchorage", "USA", "America/Anchorage", "ANC"),
            City("los_angeles", "Los Angeles", "USA", "America/Los_Angeles", "LAX"),
            City("denver", "Denver", "USA", "America/Denver", "DEN"),
            City("chicago", "Chicago", "USA", "America/Chicago", "CHI"),
            City("new_york", "New York", "USA", "America/New_York", "NYC"),
            City("rio", "Rio de Janeiro", "Brazil", "America/Sao_Paulo", "RIO"),
            City("reykjavik", "Reykjavik", "Iceland", "Atlantic/Reykjavik", "REY"),
            City("nairobi", "Nairobi", "Kenya", "Africa/Nairobi", "NBO"),
            City("johannesburg", "Johannesburg", "South Africa", "Africa/Johannesburg", "JNB")
        )

        private const val SELECTED_CITIES_KEY = "selected_city_ids"
        private val DEFAULT_SELECTIONS = listOf("new_york", "london", "tokyo", "sydney")
    }

    fun getSelectedCities(): List<City> {
        val savedIds = prefs.getStringSet(SELECTED_CITIES_KEY, null)
        val idsToUse = savedIds?.toList() ?: DEFAULT_SELECTIONS
        // Maintain a reliable sorted order matching our ALL_CITIES list, or as customized.
        // For a beautiful UX, we return selected cities matching the sorted user selection.
        return ALL_CITIES.filter { idsToUse.contains(it.id) }
    }

    fun addCity(cityId: String): Boolean {
        val currentSelections = getSelectedCities().map { it.id }.toMutableSet()
        if (currentSelections.add(cityId)) {
            prefs.edit().putStringSet(SELECTED_CITIES_KEY, currentSelections).apply()
            return true
        }
        return false
    }

    fun removeCity(cityId: String): Boolean {
        val currentSelections = getSelectedCities().map { it.id }.toMutableSet()
        if (currentSelections.remove(cityId)) {
            prefs.edit().putStringSet(SELECTED_CITIES_KEY, currentSelections).apply()
            return true
        }
        return false
    }

    fun searchCities(query: String): List<City> {
        if (query.isEmpty()) return ALL_CITIES
        val lowerQuery = query.lowercase()
        return ALL_CITIES.filter {
            it.name.lowercase().contains(lowerQuery) ||
            it.country.lowercase().contains(lowerQuery) ||
            it.abbreviation.lowercase().contains(lowerQuery)
        }
    }
}
