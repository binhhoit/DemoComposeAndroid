package com.saigon.compose.ui.animation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CallEnd
import androidx.compose.material.icons.filled.MicOff
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionLayoutDebugFlags
import androidx.constraintlayout.compose.MotionScene
import coil.compose.rememberAsyncImagePainter
import com.saigon.compose.R
import java.util.*

@Preview
@OptIn(ExperimentalMotionApi::class)
@Composable
fun ScreenDemoDynamicIsland() {
    Scaffold {
        val context = LocalContext.current
        val motionScene = remember {
            context.resources
                .openRawResource(R.raw.motion_dynamic_island)
                .readBytes()
                .decodeToString()
        }

        var animateToEnd by remember {
            mutableStateOf(false)
        }

        val progress by animateFloatAsState(
            targetValue = if (animateToEnd) 1f else 0f,
            animationSpec = tween(6000)
        )

        Column(Modifier.fillMaxSize()) {
            MotionLayout(
                motionScene = MotionScene(content = motionScene),
                modifier = Modifier.fillMaxWidth(),
                debug = EnumSet.of(MotionLayoutDebugFlags.UNKNOWN),
                progress = progress
            ) {
                val properties = motionProperties(id = "bgIsland")
                Spacer(
                    modifier = Modifier.layoutId("bgIsland")
                        .background(
                            color = Color.Black,
                            shape = RoundedCornerShape(properties.value.int("border"))
                        )
                )
                Icon(
                    modifier = Modifier.layoutId("icPhone"),
                    imageVector = Icons.Filled.Phone,
                    tint = Color.White,
                    contentDescription = "null"
                )
                Text(
                    modifier = Modifier.layoutId("tvHour"),
                    text = "5 : 55",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Light
                )
                Icon(
                    modifier = Modifier.layoutId("icMic"),
                    imageVector = Icons.Filled.MicOff,
                    contentDescription = "null",
                    tint = Color.Red
                )

                Image(
                    painter = rememberAsyncImagePainter(stringResource(R.string.image_avatar)),
                    contentDescription = null,
                    modifier = Modifier
                        .layoutId("ivAvatar")
                        .size(60.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.White, CircleShape)
                )

                Column(modifier = Modifier.layoutId("tvName")) {
                    Text(
                        text = "Ho Thanh",
                        color = Color.White,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Light
                    )
                    Text(
                        text = "Binh",
                        color = Color.White,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Column(
                    modifier = Modifier.layoutId("icPhoneActive")
                        .background(
                            color = Color(
                                0xFF4CAF50
                            ),
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        modifier = Modifier.padding(7.dp).size(30.dp),
                        imageVector = Icons.Filled.Phone,
                        contentDescription = "null",
                        tint = Color.White
                    )
                }

                Column(
                    modifier = Modifier.layoutId("icPhoneInactive")
                        .background(
                            color = Color.Red,
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        modifier = Modifier.padding(7.dp).size(30.dp),
                        imageVector = Icons.Filled.CallEnd,
                        contentDescription = "null",
                        tint = Color.White
                    )
                }
            }
        }
    }
}
