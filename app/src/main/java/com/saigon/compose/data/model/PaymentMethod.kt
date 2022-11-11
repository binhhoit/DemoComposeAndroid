package com.saigon.compose.data.model

import com.google.gson.annotations.SerializedName

data class PaymentMethods(@SerializedName("data") val data: List<PaymentMethodCustomer>)

data class PaymentMethodCustomer(
    @SerializedName("id") val id: String,
    @SerializedName("card") val card: Card,
    @SerializedName("isSelected") val isSelected: Boolean
)

fun PaymentMethodCustomer.image() = run {
    when (card.brand) {
        "visa" -> "https://cdn.visa.com/v2/assets/images/logos/visa/blue/logo.png"
        "mastercard" -> "https://cdn.vox-cdn.com/thumbor/MiewFh33AuDEx_3sHqHBgbqEMzo=/0x0:1000x1000/320x213/filters:focal(421x430:581x590)/cdn.vox-cdn.com/uploads/chorus_image/image/62800797/Mastercard_logo.0.jpg"
        "amex" -> "https://www.aexp-static.com/cdaas/one/statics/axp-static-assets/1.8.0/package/dist/img/logos/dls-logo-bluebox-solid.png"
        "jcb" -> "https://upload.wikimedia.org/wikipedia/commons/thumb/4/40/JCB_logo.svg/150px-JCB_logo.svg.png?20160827145146"
        "discover" -> "https://cdn.visa.com/v2/assets/images/logos/visa/blue/logo.png"
        else -> ""
    }
}
