package com.saigon.compose.ui.topbar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.saigon.compose.ui.theme.white

@Composable
fun TopBarApp(
    navController: NavController,
    topBarState: MutableState<Boolean>,
    title: String
) {
    AnimatedVisibility(
        visible = topBarState.value,
        enter = slideInVertically(initialOffsetY = { -it }),
        exit = slideOutVertically(targetOffsetY = { -it }),
        content = {
            TopAppBar(
                backgroundColor = Color(0xFF000000),
                title = {
                    Text(
                        textAlign = TextAlign.Center,
                        text = title,
                        color = white,
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.h5
                    )
                }
            )
        }
    )
}
