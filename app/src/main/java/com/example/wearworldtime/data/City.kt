package com.example.wearworldtime.data

import java.io.Serializable

data class City(
    val id: String,
    val name: String,
    val country: String,
    val timeZoneId: String,
    val abbreviation: String
) : Serializable
