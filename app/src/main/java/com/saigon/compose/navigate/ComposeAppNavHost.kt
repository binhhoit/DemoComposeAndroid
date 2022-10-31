package com.saigon.compose.navigate

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.saigon.compose.ui.HomeScreen
import com.saigon.compose.ui.login.LoginScreen
import com.saigon.compose.ui.login.LoginViewModelImpl

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
            LoginScreen(modifier = Modifier, viewModel = LoginViewModelImpl()) {
                navController.navigate(Screen.home)
            }
        }
        composable(Screen.home) { HomeScreen(modifier = Modifier) }
    }
}
