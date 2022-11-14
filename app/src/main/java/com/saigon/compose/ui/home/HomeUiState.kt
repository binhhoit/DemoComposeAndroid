package com.saigon.compose.ui.home

import com.saigon.compose.data.model.DashboardData

data class HomeUiState(
    val data: DashboardData? = null,
    val isLoading: Boolean = false,
    val throwError: Boolean = false
)
