package com.saigon.compose.ui.shop

import com.saigon.compose.data.model.Product

data class ShopUiState(
    val products: List<Product> = listOf(),
    val isLoading: Boolean = false,
    val throwError: Boolean = false
)
