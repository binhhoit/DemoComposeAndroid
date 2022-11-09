package com.saigon.compose.ui.product_details

import androidx.lifecycle.viewModelScope
import com.saigon.compose.data.local.SharePreferenceManager
import com.saigon.compose.data.model.Product
import com.saigon.compose.data.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class ProductDetailsViewModelImpl(
    private val repository: ProductRepository,
    private val localManager: SharePreferenceManager
) : ProductDetailsViewModel() {

    init {
        Timber.d("load product: Open")
    }

    override fun getProductFindById(id: String) {
        viewModelScope.launch(Dispatchers.Main) {
            Timber.d("load product: $id")
            val result = repository.getProducts()
            productState.value = ProductDetailsUiState(
                product = result.find { it.id.equals(id) } ?: Product(),
                isLoading = false
            )
        }
    }

    override fun addProductToCartId(item: Product) {
        localManager.saveProductToCart(item)
    }

    override fun onCleared() {
        super.onCleared()
        Timber.d("load product: Close")
    }
}
