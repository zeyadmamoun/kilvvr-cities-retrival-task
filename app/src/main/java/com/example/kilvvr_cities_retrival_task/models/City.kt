package com.example.kilvvr_cities_retrival_task.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class City(
    @SerialName(value = "_id")
    val id: Int,
    val name: String,
    val country: String,
    val coord: Coordinates
)
