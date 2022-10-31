package com.saigon.compose.ui.login

data class LoginUiState(
    val loginSuccess: String = "",
    val isLoading: Boolean = false,
    val throwError: Boolean = false
)