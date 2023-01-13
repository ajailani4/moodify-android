package com.ajailani.moodify.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ajailani.moodify.ui.common.SharedViewModel
import com.ajailani.moodify.ui.feature.add_edit_mood.AddEditMoodScreen
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
    sharedViewModel: SharedViewModel
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
                onNavigateUp = { navController.navigateUp() },
                onNavigateToMoodDetail = { moodId ->
                    navController.navigate(
                        Screen.MoodDetail.route + "?moodId=$moodId"
                    )
                }
            )
        }

        composable(
            route = Screen.MoodDetail.route + "?moodId={moodId}",
            arguments = listOf(
                navArgument("moodId") {
                    type = NavType.StringType
                }
            )
        ) {
            MoodDetailScreen(
                onNavigateUp = { navController.navigateUp() },
                onNavigateToAddEditMood = { moodId ->
                    navController.navigate(
                        Screen.AddEditMood.route + "?moodId=$moodId"
                    )
                }
            )
        }

        composable(
            route = Screen.AddEditMood.route + "?moodId={moodId}",
            arguments = listOf(
                navArgument("moodId") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) {
            AddEditMoodScreen(
                sharedViewModel = sharedViewModel,
                onNavigateUp = { navController.navigateUp() }
            )
        }

        // Bottom Nav Bar Menu Routes
        composable(Screen.Home.route) {
            HomeScreen(
                sharedViewModel = sharedViewModel,
                onNavigateToMoodList = { navController.navigate(Screen.MoodList.route) },
                onNavigateToMoodDetail = { moodId ->
                    navController.navigate(
                        Screen.MoodDetail.route + "?moodId=$moodId"
                    )
                },
                onNavigateToAddEditMood = { navController.navigate(Screen.AddEditMood.route) }
            )
        }

        composable(Screen.Statistic.route) {

        }

        composable(Screen.Profile.route) {

        }
    }
}