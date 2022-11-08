package com.saigon.compose.ui.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saigon.compose.data.repository.ProductRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class LoginViewModel : ViewModel() {
    val loginUiState = mutableStateOf(LoginUiState(isLoading = false))
    abstract fun verifyAccountLogin(userName: String, pass: String)
}

class LoginViewModelImpl(val data: ProductRepository) : LoginViewModel() {

    init {
        viewModelScope.launch {
            val raw = data.getProducts()
            Timber.d("data products" + raw.size.toString())
        }
    }
    override fun verifyAccountLogin(userName: String, pass: String) {
        viewModelScope.launch {
            loginUiState.value = LoginUiState(isLoading = true)
            delay(3000)
            loginUiState.value = LoginUiState(loginSuccess = "Account $userName ***${pass.last()} authentication success")
        }
    }
}
