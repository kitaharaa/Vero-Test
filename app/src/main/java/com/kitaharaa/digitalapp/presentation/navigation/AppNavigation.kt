package com.kitaharaa.digitalapp.presentation.navigation

sealed class AppNavigation(val route: String) {
    data object Home: AppNavigation("home")
    data object Search: AppNavigation("search")
    data object QrReader: AppNavigation("qr_reader")
}