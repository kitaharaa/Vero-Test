package com.kitaharaa.digitalapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kitaharaa.digitalapp.common.theme.VeroTaskTheme
import com.kitaharaa.digitalapp.presentation.home.HomeScreen
import com.kitaharaa.digitalapp.presentation.navigation.AppNavigation
import com.kitaharaa.digitalapp.presentation.qr.QrReaderScreen
import com.kitaharaa.digitalapp.presentation.search.SearchScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VeroTaskTheme {
                val navController = rememberNavController()
                NavHost(navController, AppNavigation.Home.route) {
                    composable(AppNavigation.Home.route) {
                        HomeScreen()
                    }
                    composable(AppNavigation.Search.route) {
                        SearchScreen()
                    }
                    composable(AppNavigation.QrReader.route) {
                        QrReaderScreen()
                    }
                }
            }
        }
    }
}