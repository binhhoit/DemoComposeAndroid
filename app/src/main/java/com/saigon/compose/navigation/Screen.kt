package com.saigon.compose.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Home : Screen("home")
    object Setting : Screen("setting")
    object Profile : Screen("profile")
    object SettingDetails : Screen("setting_details")
}
