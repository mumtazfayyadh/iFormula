package com.mumtazfayyadh0102.iformula.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mumtazfayyadh0102.iformula.screen.AboutScreen
import com.mumtazfayyadh0102.iformula.screen.CalculateScreen
import com.mumtazfayyadh0102.iformula.screen.CircuitScreen
import com.mumtazfayyadh0102.iformula.screen.DriverScreen
import com.mumtazfayyadh0102.iformula.screen.GalleryScreen
import com.mumtazfayyadh0102.iformula.screen.HomeScreen
import com.mumtazfayyadh0102.iformula.screen.WhatIsF1Screen

object NavigationRoute {
    const val HOME = "home"
    const val WHAT_IS_F1 = "what_is_f1"
    const val DRIVERS = "drivers"
    const val CIRCUIT = "circuit"
    const val GALLERY = "gallery"
    const val CALCULATE = "calculate"
    const val ABOUT = "about"
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavigationRoute.HOME
    ) {
        composable(NavigationRoute.HOME) {
            HomeScreen(navController = navController)
        }
        composable(NavigationRoute.WHAT_IS_F1) {
            WhatIsF1Screen(navController = navController)
        }
        composable(NavigationRoute.DRIVERS) {
            DriverScreen(navController = navController)
        }
        composable(NavigationRoute.CIRCUIT) {
            CircuitScreen(navController = navController)
        }
        composable(NavigationRoute.GALLERY) {
            GalleryScreen(navController = navController)
        }
        composable(NavigationRoute.CALCULATE) {
            CalculateScreen(navController = navController)
        }
        composable(NavigationRoute.ABOUT) {
            AboutScreen(navController = navController)
        }
    }
}

