package com.saigon.compose.data.repository

import com.saigon.compose.data.model.Product
import kotlinx.coroutines.flow.Flow

abstract class ProductRepository {

    abstract suspend fun getProducts()
}
