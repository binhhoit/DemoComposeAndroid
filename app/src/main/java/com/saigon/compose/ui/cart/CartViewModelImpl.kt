package com.saigon.compose.ui.cart

import androidx.lifecycle.viewModelScope
import com.saigon.compose.data.local.SharePreferenceManager
import com.saigon.compose.data.model.Product
import com.saigon.compose.data.repository.ProductRepository
import kotlinx.coroutines.launch

class CartViewModelImpl(
    private val repository: ProductRepository,
    private val localManager: SharePreferenceManager
) : CartViewModel() {
    override fun getListProductAddToCart() {
        viewModelScope.launch {
            val result = localManager.getListProductsToCart()
            productState.value = CartUiState(products = result)
        }
    }

    override fun removeProductAddToCart(item: Product) {
        localManager.saveProductsToCart(listOf(item))
    }

    override fun clearDataCart() {
        localManager.saveProductsToCart(listOf())
    }

    override fun addProductAddToCart(item: Product) {
        localManager.saveProductsToCart(listOf(item))
    }
}
