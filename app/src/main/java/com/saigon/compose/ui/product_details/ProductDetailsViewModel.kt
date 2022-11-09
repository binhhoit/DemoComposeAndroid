package com.saigon.compose.ui.product_details

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.saigon.compose.data.model.Product

abstract class ProductDetailsViewModel : ViewModel() {
    val productState = mutableStateOf(ProductDetailsUiState(isLoading = true))
    abstract fun getProductFindById(id: String)
    abstract fun addProductToCartId(item: Product)
}
