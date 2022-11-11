package com.saigon.compose.ui.payment.method

import com.saigon.compose.data.model.PaymentMethods

data class PaymentMethodUiState(
    val paymentMethods: PaymentMethods = PaymentMethods(listOf()),
    val isLoading: Boolean = false,
    val throwError: Boolean = false
)
