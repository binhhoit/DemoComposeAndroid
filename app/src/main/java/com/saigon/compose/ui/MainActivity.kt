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
            backgroundColor = Color(0xFFF0EAE2),
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
        ) { padding ->
            ComposeAppNavHost(
                modifier = Modifier.padding(padding),
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

    // Control TopBar and BottomBar
    when (navBackStackEntry?.destination?.route) {
        Screen.login -> {
            // Show BottomBar and TopBar
            bottomBarState.value = false
            topBarState.value = false
        }
        Screen.home -> {
            bottomBarState.value = true
            topBarState.value = false
        }
        else -> {
            // Show BottomBar and TopBar
            bottomBarState.value = true
            topBarState.value = true
        }
    }
}
