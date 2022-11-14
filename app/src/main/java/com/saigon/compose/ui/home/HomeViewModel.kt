package com.saigon.compose.ui.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

abstract class HomeViewModel : ViewModel() {
    val dashboardState = mutableStateOf(HomeUiState(isLoading = true))

    abstract fun getDashboard()
}
