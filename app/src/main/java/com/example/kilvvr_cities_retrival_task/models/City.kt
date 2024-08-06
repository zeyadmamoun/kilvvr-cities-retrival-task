package com.example.kilvvr_cities_retrival_task.models

import kotlinx.serialization.Serializable

@Serializable
data class City(
    val id: Int,
    val name: String,
    val country: String,
    val coord: Coordinates
)
