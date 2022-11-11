package com.saigon.compose.data.network

import com.saigon.compose.data.model.PaymentMethods
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceAPI {
    @GET("/v1/payment_methods")
    suspend fun getListPaymentMethod(
        @Query("type") type: String,
        @Query("customer") idCustomer: String
    ): ApiResponse<PaymentMethods>
}
