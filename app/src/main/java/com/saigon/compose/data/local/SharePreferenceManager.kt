package com.saigon.compose.data.local

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.saigon.compose.data.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SharePreferenceManager(context: Context) {
    companion object {
        private const val SHARED_PREF_NAME = "shared_pref"
        private const val PRODUCT_KEY = "product_key"

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
}