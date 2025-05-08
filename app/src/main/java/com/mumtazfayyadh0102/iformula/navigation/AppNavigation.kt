package com.mumtazfayyadh0102.iformula.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mumtazfayyadh0102.iformula.ui.screen.AboutScreen
import com.mumtazfayyadh0102.iformula.ui.screen.CalculateScreen
import com.mumtazfayyadh0102.iformula.ui.screen.CircuitScreen
import com.mumtazfayyadh0102.iformula.ui.screen.DriverScreen
import com.mumtazfayyadh0102.iformula.ui.screen.FormScreen
import com.mumtazfayyadh0102.iformula.ui.screen.GalleryScreen
import com.mumtazfayyadh0102.iformula.ui.screen.HomeScreen
import com.mumtazfayyadh0102.iformula.ui.screen.NotesScreen
import com.mumtazfayyadh0102.iformula.ui.screen.WhatIsF1Screen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(Screen.WhatIsF1.route) {
            WhatIsF1Screen(navController = navController)
        }
        composable(Screen.Drivers.route) {
            DriverScreen(navController = navController)
        }
        composable(Screen.Circuit.route) {
            CircuitScreen(navController = navController)
        }
        composable(Screen.Gallery.route) {
            GalleryScreen(navController = navController)
        }
        composable(Screen.Calculate.route) {
            CalculateScreen(navController = navController)
        }
        composable(Screen.About.route) {
            AboutScreen(navController = navController)
        }
        composable(Screen.Notes.route) {
            NotesScreen(navController = navController)
        }
        composable(Screen.Form.route) {
            FormScreen(navController = navController, noteId = null) // untuk tambah
        }
        composable("formScreen/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toIntOrNull()
            FormScreen(navController = navController, noteId = id) // untuk edit
        }
    }
}