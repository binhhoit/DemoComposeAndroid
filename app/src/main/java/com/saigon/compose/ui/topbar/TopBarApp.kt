package com.saigon.compose.ui.topbar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.saigon.compose.navigation.Screen
import com.saigon.compose.ui.theme.white

@Composable
fun TopBarApp(
    navController: NavController,
    topBarState: MutableState<Boolean>,
    title: String
) {
    AnimatedVisibility(
        visible = topBarState.value,
       /* enter = slideInVertically(initialOffsetY = { -it }),
        exit = slideOutVertically(targetOffsetY = { -it }),*/
        content = {
            Column {
                Spacer(modifier = Modifier.fillMaxWidth().height(20.dp).background(Color.Black))
                TopAppBar(
                    backgroundColor = Color(0xFF000000),
                    title = {
                        Text(
                            textAlign = TextAlign.Center,
                            text =
                            if (title.contains(Screen.ProductDetails.route)) {
                                navController.currentBackStackEntry?.arguments?.getString(
                                    "title"
                                ) ?: ""
                            } else title,
                            color = white,
                            modifier = Modifier.fillMaxWidth(),
                            style = MaterialTheme.typography.subtitle1,
                            fontSize = 18.sp
                        )
                    },
                    navigationIcon = if (title.contains(Screen.ProductDetails.route)) {
                        {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = null,
                                    tint = Color.White
                                )
                            }
                        }
                    } else {
                        null
                    }
                )
            }
        }

    )
}
