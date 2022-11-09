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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.saigon.compose.navigation.Screen
import com.saigon.compose.ui.theme.orange
import com.saigon.compose.ui.theme.red

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
                backgroundColor = Color.White,
                modifier = modifier
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                BottomNavigationItemCustom(
                    Screen.Home.route,
                    navController = navController,
                    currentDestination = currentDestination,
                    iconVector = Icons.Default.Home,
                    screenDestination = Screen.Home.route
                )

                BottomNavigationItemCustom(
                    Screen.Shop.route,
                    navController = navController,
                    currentDestination = currentDestination,
                    iconVector = Icons.Default.Build,
                    screenDestination = Screen.Shop.route
                )

                BottomNavigationItemCustom(
                    Screen.Cart.route,
                    navController = navController,
                    currentDestination = currentDestination,
                    iconVector = Icons.Default.ShoppingCart,
                    screenDestination = Screen.Cart.route
                )

                BottomNavigationItemCustom(
                    Screen.Setting.route,
                    navController = navController,
                    currentDestination = currentDestination,
                    iconVector = Icons.Default.AccountCircle,
                    screenDestination = Screen.Setting.route
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
        selectedContentColor = red,
        unselectedContentColor = Color.Black,
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
