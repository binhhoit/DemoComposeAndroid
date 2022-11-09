package com.saigon.compose.navigation

sealed class Screen(val route: String) {
    object Login : Screen("Login")
    object Home : Screen("Home")
    object Setting : Screen("Setting")
    object Profile : Screen("Profile")
    object Shop : Screen("Shop")
    object SettingDetails : Screen("Setting Details")
    object ProductDetails : Screen("Product Details")
    object Cart : Screen("Cart")
}
