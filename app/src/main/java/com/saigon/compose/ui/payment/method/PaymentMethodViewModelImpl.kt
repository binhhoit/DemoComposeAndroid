package com.saigon.compose.ui.payment.method

import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.saigon.compose.data.local.SharePreferenceManager
import com.saigon.compose.data.repository.PaymentRepository
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onFailure
import com.skydoves.sandwich.onSuccess
import kotlinx.coroutines.launch
import timber.log.Timber

class PaymentMethodViewModelImpl(
    private val repository: PaymentRepository
) : PaymentMethodViewModel() {
    override fun getPaymentMethods() {
        viewModelScope.launch {
            repository.getListPaymentMethod("card", "cus_ME024i5PBPSA4P")
                .onError {
                    Timber.e(Gson().toJson(this.message()))
                }.onSuccess {
                    Timber.d(Gson().toJson(this.data))
                    paymentMethodsState.value = PaymentMethodUiState(this.data)
                }.onFailure {
                    Timber.e(Gson().toJson(this))
                }
        }
    }
}
