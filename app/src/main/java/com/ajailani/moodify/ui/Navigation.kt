package com.ajailani.moodify.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ajailani.moodify.ui.feature.register.RegisterScreen
import com.ajailani.moodify.ui.feature.welcome.WelcomeScreen

@Composable
fun Navigation(
    navController: NavHostController,
    startDestination: String,
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(Screen.Welcome.route) {
            WelcomeScreen(
                onNavigateToRegister = { navController.navigate(Screen.Register.route) }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}