package com.saigon.compose.data.model

import com.google.gson.annotations.SerializedName

data class Card(
    @SerializedName("brand") val brand: String,
    @SerializedName("country") val country: String,
    @SerializedName("expYear") val expYear: String,
    @SerializedName("expMonth") val expMonth: String,
    @SerializedName("funding") val funding: String,
    @SerializedName("last4") val last4: String
)
