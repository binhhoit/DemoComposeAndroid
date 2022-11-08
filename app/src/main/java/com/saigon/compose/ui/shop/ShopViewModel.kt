package com.saigon.compose.ui.shop

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

abstract class ShopViewModel : ViewModel() {
    val productState = mutableStateOf(ShopUiState(isLoading = true))
    abstract fun getListProduct()
}
