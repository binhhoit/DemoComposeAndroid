package com.saigon.compose.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DashboardData(
    @SerializedName("new_products") var newProducts: List<Product>,
    @SerializedName("sale_products") var saleProducts: List<Product>,
    @SerializedName("promotion") var promotion: Product
) : Serializable {
    constructor() : this(
        newProducts = listOf(),
        saleProducts = listOf(),
        promotion = Product()
    )
}
