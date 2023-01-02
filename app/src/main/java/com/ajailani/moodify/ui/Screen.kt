package com.ajailani.moodify.ui

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome_screen")
}
