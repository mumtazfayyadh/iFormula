package com.mumtazfayyadh0102.iformula.navigation

sealed class Screen(val route: String) {
    object Home : Screen("homeScreen")
    object WhatIsF1 : Screen("whatIsF1Screen")
    object Drivers : Screen("driversScreen")
    object Circuit : Screen("circuitScreen")
    object Gallery : Screen("galleryScreen")
    object Calculate : Screen("calculateScreen")
    object About : Screen("aboutScreen")
    object Notes : Screen("notesScreen")
    object Form : Screen("formScreen") {
        fun withId(id: Int) = "formScreen/$id"
    }
}