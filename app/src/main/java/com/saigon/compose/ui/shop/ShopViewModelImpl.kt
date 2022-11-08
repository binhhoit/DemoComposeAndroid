package com.saigon.compose.ui.shop

import androidx.lifecycle.viewModelScope
import com.saigon.compose.data.repository.ProductRepository
import kotlinx.coroutines.launch
import timber.log.Timber

class ShopViewModelImpl(private val repository: ProductRepository) : ShopViewModel() {
    override fun getListProduct() {
        viewModelScope.launch {
            val result = repository.getProducts()
            productState.value = ShopUiState(products = result, isLoading = false)
        }
    }

    override fun getListProductCategory(category: String) {
        Timber.d("load product: $category")
        viewModelScope.launch {
            val result = repository.getProducts()
            productState.value = ShopUiState(
                products = result.filter { it.category.equals(category) },
                isLoading = false
            )
        }
    }
}
