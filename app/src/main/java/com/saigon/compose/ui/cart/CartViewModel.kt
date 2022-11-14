package com.saigon.compose.ui.cart

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.saigon.compose.data.model.Product

abstract class CartViewModel : ViewModel() {
    val productState = mutableStateOf(CartUiState(isLoading = true))
    abstract fun getListProductAddToCart()
    abstract fun removeProductAddToCart(item: Product)
    abstract fun clearDataCart()
    abstract fun addProductAddToCart(item: Product)
}
