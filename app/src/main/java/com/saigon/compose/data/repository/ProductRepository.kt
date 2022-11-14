package com.saigon.compose.data.repository

import com.saigon.compose.data.model.DashboardData
import com.saigon.compose.data.model.Product

abstract class ProductRepository {
    abstract suspend fun getProducts(): List<Product>
    abstract suspend fun getDashboard(): DashboardData
}
