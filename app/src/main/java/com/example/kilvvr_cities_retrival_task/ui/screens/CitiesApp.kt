package com.example.kilvvr_cities_retrival_task.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.kilvvr_cities_retrival_task.ui.screens.home.HomeScreen
import com.example.kilvvr_cities_retrival_task.ui.screens.map.MapScreen

sealed class Screen(val route: String) {
    data object Home : Screen(route = "home_screen")
    data object Map : Screen(route = "map_screen/{lat}/{lon}"){
        fun createRoute(lat: Double, lon: Double) = "map_screen/$lat/$lon"
    }
}

@Composable
fun CitiesApp(
    navController: NavHostController = rememberNavController()
) {
    Scaffold{ innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            NavHost(
                navController = navController,
                startDestination = Screen.Home.route,
            ) {
                composable(route = Screen.Home.route) {
                    HomeScreen(
                        navController = navController
                    )
                }
                composable(
                    route = Screen.Map.route,
                    arguments = listOf(
                        navArgument("lat") { type = NavType.FloatType },
                        navArgument("lon") { type = NavType.FloatType }
                    )
                ) {
                    val lat = it.arguments?.getFloat("lat") ?: 0f
                    val lon = it.arguments?.getFloat("lon") ?: 0f
                    MapScreen(latitude = lat, longitude = lon)
                }
            }
        }
    }
}
