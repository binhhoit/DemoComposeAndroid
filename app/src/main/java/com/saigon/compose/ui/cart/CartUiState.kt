package com.saigon.compose.ui.cart

import com.saigon.compose.data.model.Product

data class CartUiState(
    val products: List<Product> = listOf(),
    var priceTotal: Int = 0,
    val isLoading: Boolean = false,
    val throwError: Boolean = false
)
