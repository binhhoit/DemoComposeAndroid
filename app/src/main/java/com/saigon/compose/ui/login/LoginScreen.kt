package com.saigon.compose.ui.login

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.saigon.compose.navigation.Screen
import com.saigon.compose.ui.theme.MyApplicationTheme
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    modifier: Modifier,
    viewModel: LoginViewModel,
    destination: (String) -> Unit
) {
    var username by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }

    val uiState = viewModel.loginUiState.value

    /*val uiState by produceState(initialValue = LoginUiState(isLoading = false)){
        val result = viewModel.loginUiState.value
        value = result
    }*/
    when {
        uiState.loginSuccess.isNotBlank() -> {
            StatusInfo(text = uiState.loginSuccess)
            LaunchedEffect(key1 = "enterKey") {
                delay(2000)
                destination.invoke(Screen.Home.route)
            }
        }
        uiState.isLoading -> {
            Box(modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    color = MaterialTheme.colors.onSurface,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
        uiState.throwError -> {
            StatusInfo(text = "Error")
        }
        else -> {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(
                    modifier = modifier
                        .padding(15.dp)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ComponentLogin(modifier = Modifier) {
                        InputInfo(
                            modifier = Modifier,
                            "User Name",
                            Icons.Default.Person,
                            username
                        ) {
                            username = it
                        }
                    }
                    ComponentLogin(modifier = Modifier) {
                        InputInfo(
                            modifier = Modifier,
                            "Password",
                            Icons.Default.Lock,
                            pass
                        ) {
                            pass = it
                        }
                    }
                    ComponentLogin(modifier = Modifier) {
                        ButtonLogin(modifier = Modifier) {
                            Log.e("Login", "Click")
                            if (username.isNotBlank() && pass.isNotBlank()) {
                                viewModel.verifyAccountLogin(
                                    userName = username,
                                    pass = pass
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ComponentLogin(modifier: Modifier, content: @Composable () -> Unit) {
    Column(modifier = modifier.padding(vertical = 15.dp)) {
        content()
    }
}

@Composable
fun StatusInfo(text: String) {
    Text(text = text)
}

@Composable
fun InputInfo(
    modifier: Modifier,
    placeholder: String,
    image: ImageVector,
    value: String,
    onValue: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValue,
        leadingIcon = {
            Icon(
                imageVector = image,
                contentDescription = null
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.surface
        ),
        placeholder = {
            Text(placeholder)
        },
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp)
    )
}

@Composable
fun ButtonLogin(modifier: Modifier, onclick: () -> Unit) {
    Button(modifier = modifier, onClick = onclick) {
        Text(text = "Login")
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        val vm:LoginViewModel = koinViewModel<LoginViewModel>()
        Scaffold(
            backgroundColor = Color(0xFFF0EAE2)
        ) { padding ->
            LoginScreen(
                modifier = Modifier.padding(padding),
                viewModel = vm
            ) {
            }
        }
    }
}
