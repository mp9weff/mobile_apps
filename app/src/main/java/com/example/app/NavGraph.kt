package com.example.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.app.ui.screens.SubjectListScreen
import com.example.app.ui.screens.SubjectDetailScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "subjects") {
        composable("subjects") {
            SubjectListScreen(navController)
        }
        composable("subject/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toLongOrNull() ?: -1L
            SubjectDetailScreen(navController, id)
        }
    }
}
