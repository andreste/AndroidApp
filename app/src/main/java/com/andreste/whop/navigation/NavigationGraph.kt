package com.andreste.whop.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.andreste.whop.screens.HomeScreen
import com.andreste.whop.viewmodels.NotificationsViewModel

@Composable
fun NavigationGraph() {
    val navController = rememberNavController()
    val viewModel: NotificationsViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = Screen.Home.name) {
        composable(Screen.Home) {
            HomeScreen(navController, viewModel)
        }
    }
}