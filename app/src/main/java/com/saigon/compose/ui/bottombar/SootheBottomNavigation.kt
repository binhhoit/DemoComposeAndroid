package com.saigon.compose.ui.bottombar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.saigon.compose.navigation.Screen

@Composable
fun SootheBottomNavigation(
    modifier: Modifier = Modifier,
    navController: NavController,
    bottomBarState: MutableState<Boolean>
) {
    AnimatedVisibility(
        visible = bottomBarState.value,
        enter = slideInVertically(initialOffsetY = { -it }),
        exit = slideOutVertically(targetOffsetY = { -it }),
        content = {
            BottomNavigation(
                backgroundColor = MaterialTheme.colors.background,
                modifier = modifier
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                BottomNavigationItemCustom(
                    "Home",
                    navController = navController,
                    currentDestination = currentDestination,
                    iconVector = Icons.Default.Home,
                    screenDestination = Screen.home
                )

                BottomNavigationItemCustom(
                    "Shop",
                    navController = navController,
                    currentDestination = currentDestination,
                    iconVector = Icons.Default.Build,
                    screenDestination = Screen.profile
                )

                BottomNavigationItemCustom(
                    "Cart",
                    navController = navController,
                    currentDestination = currentDestination,
                    iconVector = Icons.Default.ShoppingCart,
                    screenDestination = Screen.profile
                )

                BottomNavigationItemCustom(
                    "Settings",
                    navController = navController,
                    currentDestination = currentDestination,
                    iconVector = Icons.Default.AccountCircle,
                    screenDestination = Screen.setting
                )
            }
        }
    )
}

@Composable
fun RowScope.BottomNavigationItemCustom(
    title: String,
    navController: NavController,
    currentDestination: NavDestination?,
    iconVector: ImageVector,
    screenDestination: String
) {
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
