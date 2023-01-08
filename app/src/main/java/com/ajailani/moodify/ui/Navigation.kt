package com.ajailani.moodify.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ajailani.moodify.ui.feature.home.HomeScreen
import com.ajailani.moodify.ui.feature.login.LoginScreen
import com.ajailani.moodify.ui.feature.mood_detail.MoodDetailScreen
import com.ajailani.moodify.ui.feature.mood_list.MoodListScreen
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
                onNavigateToLogin = { navController.navigate(Screen.Login.route) },
                onNavigateToRegister = { navController.navigate(Screen.Register.route) }
            )
        }

        composable(Screen.Login.route) {
            LoginScreen(
                onNavigateUp = { navController.navigateUp() },
                onNavigateToRegister = { navController.navigate(Screen.Register.route) },
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        launchSingleTop = true

                        popUpTo(Screen.Welcome.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                onNavigateUp = { navController.navigateUp() },
                onNavigateToLogin = { navController.navigate(Screen.Login.route) },
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        launchSingleTop = true

                        popUpTo(Screen.Welcome.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(Screen.MoodList.route) {
            MoodListScreen(
                onNavigateUp = { navController.navigateUp() }
            )
        }

        composable(Screen.MoodDetail.route) {
            MoodDetailScreen()
        }

        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToMoodList = { navController.navigate(Screen.MoodList.route) }
            )
        }

        composable(Screen.Statistic.route) {

        }

        composable(Screen.Profile.route) {

        }
    }
}