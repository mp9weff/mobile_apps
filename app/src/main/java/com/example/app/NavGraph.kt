package com.example.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.app.ui.screens.DestinationDetailScreen
import com.example.app.ui.screens.DestinationListScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "list") {
        composable("list") {
            DestinationListScreen(navController)
        }
        composable("details/{placeName}") { backStackEntry ->
            val placeName = backStackEntry.arguments?.getString("placeName")
            DestinationDetailScreen(placeName ?: "Невідоме місце")
        }
    }
}
