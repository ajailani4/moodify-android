package com.ajailani.moodify.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ajailani.moodify.ui.feature.welcome.WelcomeScreen

@Composable
fun Navigation(
    navHostController: NavHostController,
    startDestination: String,
) {
    NavHost(navController = navHostController, startDestination = startDestination) {
        composable(Screen.Welcome.route) {
            WelcomeScreen()
        }
    }
}