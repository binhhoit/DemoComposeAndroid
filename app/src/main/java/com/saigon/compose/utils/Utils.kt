package com.saigon.compose.utils

import com.saigon.compose.data.model.Product

object Utils {
    fun List<Product>.countPriceProducts(): Int {
        var total = 0
        forEach {
            total += ((it.price ?: 0) * it.count)
        }
        return total
    }
}
