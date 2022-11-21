package com.saigon.compose.ui.animation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Slider
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.Dimension.Companion.value
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionLayoutDebugFlags
import androidx.constraintlayout.compose.MotionScene
import coil.compose.rememberAsyncImagePainter
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.saigon.compose.R
import java.util.*
import androidx.constraintlayout.compose.*


@OptIn(ExperimentalMotionApi::class, ExperimentalGlideComposeApi::class)
@Composable
fun ScreenDemoMotion() {
    val context = LocalContext.current
    val motionScene = remember {
        context.resources
            .openRawResource(R.raw.motion_scene)
            .readBytes()
            .decodeToString()
    }

    var progress by remember {
        mutableStateOf(0f)
    }

    Column(Modifier.fillMaxSize()) {
        Slider(
            modifier = Modifier.padding(horizontal = 32.dp).padding(top = 100.dp),
            value = progress,
            onValueChange = {
                progress = it
            }
        )
        MotionLayout(
            motionScene = MotionScene(content = motionScene),
            modifier = Modifier.fillMaxWidth(),
            debug = EnumSet.of(MotionLayoutDebugFlags.SHOW_ALL),
            progress = progress
        ) {
            val properties = motionProperties("iv_center")
            Image(
                painter = rememberAsyncImagePainter(R.drawable.sts),
                contentDescription = "some useful description",
                modifier = Modifier.layoutId("iv_center"),
                colorFilter = ColorFilter.tint(properties.value.color("background"))
            )
        }
    }
}

@Preview
@Composable
fun PreviewDemoMotion() {
    Scaffold {
        ScreenDemoMotion()
    }
}
