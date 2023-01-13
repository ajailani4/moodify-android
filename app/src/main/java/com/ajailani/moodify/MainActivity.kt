package com.ajailani.moodify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ajailani.moodify.ui.Navigation
import com.ajailani.moodify.ui.Screen
import com.ajailani.moodify.ui.common.SharedViewModel
import com.ajailani.moodify.ui.feature.splash.SplashViewModel
import com.ajailani.moodify.ui.theme.MoodifyTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val splashViewModel: SplashViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by viewModels {
        SavedStateViewModelFactory(application, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        lifecycleScope.launch {
            splashViewModel.getAccessToken().first().let { accessToken ->
                val startDestination = if (accessToken != "") {
                    Screen.Home.route
                } else {
                    Screen.Welcome.route
                }

                setContent {
                    MoodifyTheme {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            Content(
                                startDestination = startDestination,
                                sharedViewModel = sharedViewModel
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Content(
    startDestination: String,
    sharedViewModel: SharedViewModel
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val bottomNavBarMenus = listOf(
        Screen.Home,
        Screen.Statistic,
        Screen.Profile
    )

    Scaffold(
        bottomBar = {
            if (bottomNavBarMenus.any { it.route == currentRoute }) {
                NavigationBar {
                    bottomNavBarMenus.forEach { screen ->
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    imageVector = if (currentRoute == screen.route) {
                                        screen.filledIcon!!
                                    } else {
                                        screen.outlinedIcon!!
                                    },
                                    contentDescription = stringResource(id = screen.label!!)
                                )
                            },
                            label = {
                                Text(text = stringResource(id = screen.label!!))
                            },
                            selected = currentRoute == screen.route,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(Screen.Home.route) {
                                        saveState = true
                                    }

                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding())) {
            Navigation(
                navController = navController,
                startDestination = startDestination,
                sharedViewModel = sharedViewModel
            )
        }
    }
}