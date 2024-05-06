package com.kitaharaa.digitalapp.presentation.navigation

sealed class AppNavigation(val route: String) {
    data object Home: AppNavigation("home")
}