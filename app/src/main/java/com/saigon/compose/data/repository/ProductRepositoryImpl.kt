package com.saigon.compose.data.repository

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import com.saigon.compose.data.local.SharePreferenceManager
import com.saigon.compose.data.model.DashboardData
import com.saigon.compose.data.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductRepositoryImpl(
    private val database: FirebaseDatabase,
    private val localSharePreferenceManager: SharePreferenceManager
) : ProductRepository() {

    init {
        val ref = database.getReference("/products")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                handleDataProducts(snapshot)
            }

            override fun onCancelled(error: DatabaseError) {
                throw Exception(error.message)
            }
        })

        val refDashboard = database.getReference("/dashboard")
        refDashboard.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                handleDataDashboard(snapshot)
            }

            override fun onCancelled(error: DatabaseError) {
                throw Exception(error.message)
            }
        })
    }

    private fun handleDataProducts(snapshot: DataSnapshot) {
        val products = mutableListOf<Product>()
        if (snapshot.hasChildren()) {
            snapshot.children.forEach { chil ->
                if (chil.hasChildren()) {
                    val category = chil.key as String
                    chil.children.forEach {
                        products.add(
                            (it.getValue(Product::class.java) ?: Product("null"))
                                .apply {
                                    this.category = category
                                }
                        )
                    }
                }
            }
        }
        localSharePreferenceManager.saveProductsData(Gson().toJson(products))
    }

    private fun handleDataDashboard(snapshot: DataSnapshot) {
        val data = snapshot.getValue(Any::class.java)
        localSharePreferenceManager.saveDataDashboard(Gson().toJson(data))
    }

    override suspend fun getProducts() = localSharePreferenceManager.getProductsData()

    override suspend fun getDashboard(): DashboardData = localSharePreferenceManager.getDataDashboard()
}
