package com.saigon.compose.data.local

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.saigon.compose.data.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SharePreferenceManager(context: Context) {
    companion object {
        private const val SHARED_PREF_NAME = "shared_pref"
        private const val PRODUCT_KEY = "product_key"
        private const val ADD_CART = "add_cart"

        // For Singleton instantiation
        @Volatile
        private var instance: SharePreferenceManager? = null

        fun getInstance(context: Context): SharePreferenceManager {
            return instance ?: synchronized(this) {
                instance ?: SharePreferenceManager(context).also {
                    instance = it
                }
            }
        }
    }

    private val sharedPreferences by lazy(LazyThreadSafetyMode.NONE) {
        context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
    }

    private inline fun SharedPreferences.put(body: SharedPreferences.Editor.() -> Unit) {
        val editor = this.edit()
        editor.body()
        editor.apply()
    }

    fun saveProductsData(data: String) {
        sharedPreferences.put {
            putString(PRODUCT_KEY, data)
        }
    }

    suspend fun getProductsData(): List<Product> = withContext(Dispatchers.IO) {
        val raw = sharedPreferences.getString(PRODUCT_KEY, "")
        try {
            Gson().fromJson(raw, Array<Product>::class.java).toList()
        } catch (e: Exception) {
            listOf()
        }
    }

    fun saveProductToCart(product: Product) {
        val raw = sharedPreferences.getString(ADD_CART, "")
        val data = try {
            if (raw.isNullOrBlank()) {
                mutableListOf(product)
            } else {
                Gson().fromJson(raw, Array<Product>::class.java).toMutableList()
                    .apply {
                        add(product)
                    }
            }
        } catch (e: Exception) {
            listOf(Product())
        }

        sharedPreferences.put {
            putString(ADD_CART, Gson().toJson(data))
        }
    }
}
