package com.saigon.compose.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Product(val id: String? = null) : Serializable {
    constructor() : this(id = "null")

    @SerializedName("images")
    var images: List<String> = listOf()

    @SerializedName("is_sale")
    var isSale: Boolean? = null

    @SerializedName("subTitle")
    var subTitle: String? = null

    @SerializedName("title")
    var title: String? = null

    @SerializedName("is_new")
    var isNew: Boolean? = null

    @SerializedName("thumb")
    var thumb: String? = null

    @SerializedName("price")
    var price: Int? = null

    @SerializedName("rating")
    var rating: Int? = null

    @SerializedName("description")
    var description: String? = null

    @SerializedName("discount_percent")
    var discountPercent: String? = null

    @SerializedName("category")
    var category: String? = null

    @SerializedName("count")
    var count: Int = 1
}
