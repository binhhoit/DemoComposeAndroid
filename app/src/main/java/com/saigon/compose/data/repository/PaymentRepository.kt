package com.saigon.compose.data.repository

import com.saigon.compose.data.model.PaymentMethods
import com.skydoves.sandwich.ApiResponse

abstract class PaymentRepository {
    abstract suspend fun getListPaymentMethod(type: String, idCustomer: String):
        ApiResponse<PaymentMethods>
}
