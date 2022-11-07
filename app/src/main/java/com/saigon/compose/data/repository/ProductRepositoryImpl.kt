package com.saigon.compose.data.repository

import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductRepositoryImpl(private val database: FirebaseDatabase) : ProductRepository() {
    override suspend fun getProducts() = withContext(Dispatchers.IO) {

    }
}
