package com.saigon.compose.ui.cart

import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.saigon.compose.data.local.SharePreferenceManager
import com.saigon.compose.data.model.Product
import com.saigon.compose.data.repository.PaymentRepository
import com.saigon.compose.data.repository.ProductRepository
import com.saigon.compose.utils.Utils.countPriceProducts
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onFailure
import com.skydoves.sandwich.onSuccess
import kotlinx.coroutines.launch
import timber.log.Timber

class CartViewModelImpl(
    private val repository: ProductRepository,
    private val localManager: SharePreferenceManager,
    private val paymentRepository: PaymentRepository
) : CartViewModel() {
    init {
        callDemoNetWork()
    }
    override fun getListProductAddToCart() {
        viewModelScope.launch {
            val result = localManager.getListProductsToCart()
            productState.value = CartUiState(
                products = wrapProducts(result),
                priceTotal = result.countPriceProducts()
            )
        }
    }

    override fun removeProductAddToCart(item: Product) {
        val temp = productState.value.products.find { it.id == item.id }
        val list = if (temp?.count == 1) {
            productState.value.products - item
        } else {
            temp!!.count -= 1
            productState.value.products
        }
        localManager.saveProductsToCart(list)
        getListProductAddToCart()
    }

    override fun clearDataCart() {
        localManager.saveProductsToCart(listOf())
        getListProductAddToCart()
    }

    override fun addProductAddToCart(item: Product) {
        val temp = productState.value.products.find { it.id == item.id }
        val list = if (temp != null) {
            temp.count = temp.count + 1
            productState.value.products
        } else {
            productState.value.products + item
        }
        localManager.saveProductsToCart(list)
        getListProductAddToCart()
    }

    private fun wrapProducts(product: List<Product>): List<Product> {
        val productTemp = mutableListOf<Product>()
        product.forEach { item ->
            val check = productTemp.find { it.id == item.id }
            if (check != null) {
                check.count = check.count + 1
            } else {
                productTemp.add(item)
            }
        }
        return productTemp.toList()
    }

    override fun callDemoNetWork() {
        viewModelScope.launch {
            paymentRepository.getListPaymentMethod("card", "cus_ME024i5PBPSA4P")
                .onError {
                    Timber.d(Gson().toJson(this.message()))
                }.onSuccess {
                    Timber.d(Gson().toJson(this.data))
                }.onFailure {
                    Timber.d(Gson().toJson(this))
                }
        }
    }
}
