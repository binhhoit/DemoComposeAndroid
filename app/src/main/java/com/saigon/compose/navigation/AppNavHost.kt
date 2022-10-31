package com.saigon.compose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.saigon.compose.ui.home.HomeScreen
import com.saigon.compose.ui.login.LoginScreen
import com.saigon.compose.ui.login.LoginViewModelImpl
import com.saigon.compose.ui.profile.ProfileScreen
import com.saigon.compose.ui.setting.SettingsScreen
import com.saigon.compose.ui.setting.details.SettingDetailsScreen

@Composable
fun ComposeAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = Screen.login
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.login) {
            LoginScreen(modifier = modifier, viewModel = LoginViewModelImpl()) {
                navController.navigate(Screen.home)
            }
        }
        composable(Screen.home) { HomeScreen(modifier = modifier) }

        composable(Screen.profile) { ProfileScreen(modifier = modifier) }

        composable(Screen.setting) { SettingsScreen(modifier = modifier){
            navController.navigate(it)
        } }

        composable(Screen.settingDetails) { SettingDetailsScreen() }

    }
}
