package com.ajailani.moodify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.ajailani.moodify.ui.Navigation
import com.ajailani.moodify.ui.Screen
import com.ajailani.moodify.ui.feature.splash.SplashViewModel
import com.ajailani.moodify.ui.theme.MoodifyTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val splashViewModel: SplashViewModel by viewModels()

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
                            Content(startDestination)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Content(startDestination: String) {
    val navController = rememberNavController()

    Navigation(
        navController = navController,
        startDestination = startDestination
    )
}