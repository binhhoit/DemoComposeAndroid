package com.saigon.compose.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.saigon.compose.navigation.ComposeAppNavHost
import com.saigon.compose.navigation.Screen
import com.saigon.compose.ui.theme.MyApplicationTheme

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
        Scaffold(
            backgroundColor = Color(0xFFF0EAE2),
            bottomBar = { SootheBottomNavigation(navController = navController) }
        ) { padding ->
            ComposeAppNavHost(
                modifier = Modifier.padding(padding),
                navController = navController
            )
        }
    }
}

@Composable
private fun SootheBottomNavigation(modifier: Modifier = Modifier, navController: NavController) {
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.background,
        modifier = modifier
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        BottomNavigationItemCustom("Home",
            navController = navController,
            currentDestination = currentDestination,
            iconVector = Icons.Default.Home,
            screenDestination = Screen.home)

        BottomNavigationItemCustom("Profile",
            navController = navController,
            currentDestination = currentDestination,
            iconVector = Icons.Default.AccountCircle,
            screenDestination = Screen.profile)

        BottomNavigationItemCustom("Settings",
            navController = navController,
            currentDestination = currentDestination,
            iconVector = Icons.Default.Settings,
            screenDestination = Screen.setting)

    }
}

@Composable
fun RowScope.BottomNavigationItemCustom(title:String,
                               navController: NavController,
                               currentDestination: NavDestination?,
                               iconVector: ImageVector,
                               screenDestination: String) {
    BottomNavigationItem(
        icon = {
            Icon(
                imageVector = iconVector,
                contentDescription = null
            )
        },
        label = {
            Text(title)
        },
        selected = currentDestination?.hierarchy?.any { it.route == screenDestination } == true,
        onClick = {
            navController.navigate(screenDestination) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }
    )
}

