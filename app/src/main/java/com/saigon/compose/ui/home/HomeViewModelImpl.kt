package com.saigon.compose.ui.home

import androidx.lifecycle.viewModelScope
import com.saigon.compose.data.repository.ProductRepository
import kotlinx.coroutines.launch

class HomeViewModelImpl(
    private val repository: ProductRepository
) : HomeViewModel() {
    override fun getDashboard() {
        viewModelScope.launch {
            val data = repository.getDashboard()
            dashboardState.value = HomeUiState(data = data, isLoading = false)
        }
    }
}
