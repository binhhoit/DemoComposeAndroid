package com.saigon.compose.ui.payment.method

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

abstract class PaymentMethodViewModel : ViewModel() {
    val paymentMethodsState = mutableStateOf(PaymentMethodUiState(isLoading = true))

    abstract fun getPaymentMethods()
}
