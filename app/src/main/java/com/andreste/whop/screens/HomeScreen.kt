package com.andreste.whop.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.andreste.whop.viewmodels.NotificationsViewModel

@Composable
fun HomeScreen(navController: NavController, viewModel: NotificationsViewModel) {
    LaunchedEffect(Unit) {
        viewModel.start(navController)
        viewModel.getNotifications()
    }
}