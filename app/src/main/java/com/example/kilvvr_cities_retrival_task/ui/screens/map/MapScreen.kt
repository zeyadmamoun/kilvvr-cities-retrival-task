package com.example.kilvvr_cities_retrival_task.ui.screens.map

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun MapScreen(
    latitude: Float,
    longitude: Float
) {
    Text("Map showing coordinates: $latitude, $longitude")
}