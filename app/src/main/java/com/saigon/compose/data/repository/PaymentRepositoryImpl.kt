package com.saigon.compose.data.repository

import com.saigon.compose.data.network.ServiceAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PaymentRepositoryImpl(private val apiRemote: ServiceAPI) :
    PaymentRepository() {
    override suspend fun getListPaymentMethod(
        type: String,
        idCustomer: String
    ) = withContext(Dispatchers.IO) {
        apiRemote.getListPaymentMethod(type = type, idCustomer = idCustomer)
    }
}
