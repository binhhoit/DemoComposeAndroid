package com.saigon.compose.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.saigon.compose.navigation.ComposeAppNavHost
import com.saigon.compose.navigation.Screen
import com.saigon.compose.ui.bottombar.SootheBottomNavigation
import com.saigon.compose.ui.theme.MyApplicationTheme
import com.saigon.compose.ui.topbar.TopBarApp

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppCompose()
        }
    }
}

@Composable
fun AppCompose() {
    MyApplicationTheme {
        val navController = rememberNavController()
        val bottomBarState = rememberSaveable { (mutableStateOf(true)) }
        val topBarState = rememberSaveable { (mutableStateOf(true)) }

        val navBackStackEntry by navController.currentBackStackEntryAsState()

        ManagerStateTopBarAndBottomBar(
            navController = navController,
            topBarState = topBarState,
            bottomBarState = bottomBarState
        )

        Scaffold(
            backgroundColor = Color.White,
            topBar = {
                TopBarApp(
                    navController = navController,
                    topBarState = topBarState,
                    title = navBackStackEntry?.destination?.route ?: ""
                )
            },
            bottomBar = {
                SootheBottomNavigation(
                    navController = navController,
                    bottomBarState = bottomBarState
                )
            }
        ) { _ ->
            ComposeAppNavHost(
                modifier = Modifier,
                navController = navController
            )
        }
    }
}

@Composable
fun ManagerStateTopBarAndBottomBar(
    navController: NavController,
    bottomBarState: MutableState<Boolean>,
    topBarState: MutableState<Boolean>
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val system = rememberSystemUiController()

    // Control TopBar and BottomBar
    when (navBackStackEntry?.destination?.route) {
        Screen.Login.route -> {
            // Show BottomBar and TopBar
            bottomBarState.value = false
            topBarState.value = false
            //system.isStatusBarVisible = true
            system.setStatusBarColor(color = Color.Transparent)
        }
        Screen.Home.route -> {
            bottomBarState.value = true
            topBarState.value = false
            //system.isStatusBarVisible = false
           // system.statusBarDarkContentEnabled = true
        }
        else -> {
            // Show BottomBar and TopBar
            bottomBarState.value = true
            topBarState.value = true
            //system.isStatusBarVisible = true
            system.setStatusBarColor(color = Color.Black)
        }
    }
}
