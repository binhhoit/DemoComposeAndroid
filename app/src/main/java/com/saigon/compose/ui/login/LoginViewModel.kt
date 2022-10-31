package com.saigon.compose.ui.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

abstract class LoginViewModel : ViewModel() {
    val loginUiState = mutableStateOf(LoginUiState(isLoading = false))
    abstract fun verifyAccountLogin(userName: String, pass: String)
}

class LoginViewModelImpl : LoginViewModel() {
    override fun verifyAccountLogin(userName: String, pass: String) {
        viewModelScope.launch {
            loginUiState.value = LoginUiState(isLoading = true)
            delay(3000)
            loginUiState.value = LoginUiState(loginSuccess = "Account $userName ***${pass.last()} authentication success")
        }
    }
}
