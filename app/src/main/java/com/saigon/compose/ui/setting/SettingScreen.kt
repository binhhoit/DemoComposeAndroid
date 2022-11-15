package com.saigon.compose.ui.setting

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.saigon.compose.R
import com.saigon.compose.navigation.Screen
import com.saigon.compose.ui.theme.MyApplicationTheme
import com.saigon.compose.utils.LocaleUtils
import java.util.*

@Composable
fun SettingsScreen(modifier: Modifier, destination: (String) -> Unit) {
    Column(
        modifier
            .padding(15.dp)
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        SectionContent(modifier = Modifier) {
            HeaderProfile()
        }

        SectionContent(modifier = Modifier.padding(top = 30.dp)) {
            ItemSetting(
                title = "My Orders",
                des = "Already have 23 orders"
            ) {
                destination(Screen.SettingDetails.route)
            }
        }

        SectionContent(modifier = Modifier) {
            ItemSetting(title = "Payment Method", des = "Visa **** 4242") {
                destination(Screen.SettingDetails.route)
            }
        }

        SectionContent(modifier = Modifier) {
            ItemSetting(
                title = "Shipping Address",
                des = "52/08/20 xxx, 12 District, HCM City, VN"
            ) {
                destination(Screen.SettingDetails.route)
            }
        }

        SectionContent(modifier = Modifier) {
            ItemSetting(
                title = "Setting",
                des = "Notification, password, language"
            ) {
                destination(Screen.SettingDetails.route)
            }
        }

        SectionContent(modifier = Modifier) {
            ItemSwitchSetting(
                title = "Change Theme",
                des = "Dark mode or Light mode"
            ) {
            }
        }

        SectionContent(modifier = Modifier) {
            LogoutButton {
                destination(Screen.Login.route)
            }
        }
    }
}

@Composable
fun SectionContent(modifier: Modifier, content: @Composable () -> Unit) {
    Column(modifier = modifier) {
        content()
    }
}

@Composable
fun HeaderProfile() {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberAsyncImagePainter(stringResource(R.string.image_avatar)),
            contentDescription = null,
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .border(2.dp, Color.Gray, CircleShape)
        )
        Column(modifier = Modifier.padding(start = 30.dp)) {
            Text(
                text = stringResource(R.string.name_example),
                style = MaterialTheme.typography.subtitle2,
                fontSize = 13.sp
            )
            Text(
                text = stringResource(R.string.email_example),
                style = MaterialTheme.typography.body2,
                fontSize = 10.sp
            )
        }
    }
}

@Composable
fun LogoutButton(onClick: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.End,
        modifier = Modifier
            .padding(top = 5.dp)
            .fillMaxWidth()
    ) {
        Button(
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = Color.Red,
                contentColor = Color.White
            ),
            modifier = Modifier
                .height(40.dp)
                .width(150.dp),
            onClick = onClick
        ) {
            Text(
                text = stringResource(R.string.log_out),
                color = Color.White,
                style = MaterialTheme.typography.button,
                fontSize = 13.sp
            )
        }
    }
}

@Composable
fun ItemSetting(title: String, des: String, onClick: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(top = 5.dp, bottom = 5.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.subtitle2,
                fontSize = 13.sp
            )
            Text(
                text = des,
                style = MaterialTheme.typography.body2,
                fontSize = 10.sp
            )
        }
        IconButton(
            onClick = onClick
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null,
                tint = Color.Gray
            )
        }
    }

    Divider(color = Color.Gray, thickness = 0.5.dp)
}

@Composable
fun ItemSwitchSetting(title: String, des: String, onClick: () -> Unit) {
    val context = LocalContext.current
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(top = 5.dp, bottom = 5.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.subtitle2,
                fontSize = 13.sp
            )
            Text(
                text = des,
                style = MaterialTheme.typography.body2,
                fontSize = 10.sp
            )
        }
        val checkedState = remember { mutableStateOf(true) }
        Switch(
            checked = checkedState.value,
            onCheckedChange = { checkedState.value = it }
        )

        if (checkedState.value) {
            LocaleUtils.updateResources(context, "en")
        } else {
            LocaleUtils.updateResources(context, "vn")
        }
    }

    Divider(color = Color.Gray, thickness = 0.5.dp)
}

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        SettingsScreen(modifier = Modifier.padding()) {
        }
    }
}
