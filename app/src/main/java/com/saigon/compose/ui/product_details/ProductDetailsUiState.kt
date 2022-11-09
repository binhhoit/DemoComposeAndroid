package com.saigon.compose.ui.product_details

import com.saigon.compose.data.model.Product

data class ProductDetailsUiState(
    val product: Product = Product(),
    val isLoading: Boolean = false,
    val throwError: Boolean = false
)
