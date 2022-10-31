package com.saigon.compose.utils

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.saigon.compose.ui.theme.white

@Composable
fun ScaffoldExt(title: String, content: @Composable ()->Unit) {
    Scaffold(
        backgroundColor = Color.White,
        topBar = {
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
    ) {
        content()
    }
}