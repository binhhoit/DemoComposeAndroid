package com.saigon.compose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.saigon.compose.ui.home.HomeScreen
import com.saigon.compose.ui.login.LoginScreen
import com.saigon.compose.ui.login.LoginViewModel
import com.saigon.compose.ui.profile.ProfileScreen
import com.saigon.compose.ui.setting.SettingsScreen
import com.saigon.compose.ui.setting.details.SettingDetailsScreen
import com.saigon.compose.ui.shop.ShopScreen
import com.saigon.compose.ui.shop.ShopViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ComposeAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = Screen.Login.route
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Login.route) {
            val loginViewModel: LoginViewModel = koinViewModel<LoginViewModel>()
            LoginScreen(modifier = modifier, viewModel = loginViewModel) {
                navController.navigate(it)
            }
        }
        composable(Screen.Home.route) { HomeScreen(modifier = modifier) }

        composable(Screen.Profile.route) { ProfileScreen(modifier = modifier) }

        composable(Screen.Setting.route) {
            SettingsScreen(modifier = modifier) {
                navController.navigate(it)
            }
        }

        composable(Screen.Shop.route) {
            val shopViewModel = koinViewModel<ShopViewModel>()
            ShopScreen(viewModel = shopViewModel, modifier = modifier){
                navController.navigate(it)
            }
        }

        composable(Screen.SettingDetails.route) { SettingDetailsScreen() }
    }
}
