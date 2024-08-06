package com.example.kilvvr_cities_retrival_task.models

import kotlinx.serialization.Serializable

@Serializable
data class Coordinates(
    val lon: Double,
    val lat: Double
)
