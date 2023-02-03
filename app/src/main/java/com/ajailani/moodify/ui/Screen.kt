package com.ajailani.moodify.ui

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.ajailani.moodify.R

sealed class Screen(
    val route: String,
    @StringRes val label: Int? = null,
    val filledIcon: ImageVector? = null,
    val outlinedIcon: ImageVector? = null
) {
    object Welcome : Screen("welcome_screen")
    object Register : Screen("register_screen")
    object Login : Screen("login_screen")
    object MoodList : Screen("mood_list_screen")
    object MoodDetail : Screen("mood_detail_screen")
    object AddEditMood : Screen("add_edit_mood_screen")

    // Bottom Navigation Bar menu
    object Home : Screen(
        route = "home_screen",
        label = R.string.home,
        filledIcon = Icons.Default.Home,
        outlinedIcon = Icons.Outlined.Home
    )

    object Statistic : Screen(
        route = "statistic_screen",
        label = R.string.statistic,
        filledIcon = Icons.Default.BarChart,
        outlinedIcon = Icons.Outlined.BarChart
    )

    object Profile : Screen(
        route = "profile_screen",
        label = R.string.profile,
        filledIcon = Icons.Default.AccountCircle,
        outlinedIcon = Icons.Outlined.AccountCircle
    )
}
