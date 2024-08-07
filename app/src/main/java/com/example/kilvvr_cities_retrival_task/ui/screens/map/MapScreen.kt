package com.example.kilvvr_cities_retrival_task.ui.screens.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

/**
 * MapScreen Composable
 *
 * This composable function creates a screen that displays a Google Map centered on a specific location.
 *
 * @param latitude The latitude of the location to display, as a Float.
 * @param longitude The longitude of the location to display, as a Float.
 *
 * The function does the following:
 * 1. Converts the provided latitude and longitude to a LatLng object.
 * 2. Sets up a camera position state centered on the given coordinates with a zoom level of 10.
 * 3. Displays a Google Map that fills the entire screen.
 * 4. Adds a marker at the specified location.
 */
@Composable
fun MapScreen(
    latitude: Float,
    longitude: Float
) {
    val city = LatLng(latitude.toDouble(), longitude.toDouble())
    val cameraPositionState = rememberCameraPositionState{
        position = CameraPosition.fromLatLngZoom(city,10f)
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ){
        Marker(
            state = MarkerState(position = city),
        )
    }
}