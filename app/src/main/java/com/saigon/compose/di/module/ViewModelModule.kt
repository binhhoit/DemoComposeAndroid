package com.saigon.compose.di.module

import com.saigon.compose.ui.cart.CartViewModel
import com.saigon.compose.ui.cart.CartViewModelImpl
import com.saigon.compose.ui.login.LoginViewModel
import com.saigon.compose.ui.login.LoginViewModelImpl
import com.saigon.compose.ui.payment.method.PaymentMethodViewModel
import com.saigon.compose.ui.payment.method.PaymentMethodViewModelImpl
import com.saigon.compose.ui.product_details.ProductDetailsViewModel
import com.saigon.compose.ui.product_details.ProductDetailsViewModelImpl
import com.saigon.compose.ui.shop.ShopViewModel
import com.saigon.compose.ui.shop.ShopViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel<LoginViewModel> { LoginViewModelImpl(get()) }
    viewModel<ShopViewModel> { ShopViewModelImpl(get()) }
    viewModel<ProductDetailsViewModel> { ProductDetailsViewModelImpl(get(), get()) }
    viewModel<CartViewModel> { CartViewModelImpl(get(), get()) }
    viewModel<PaymentMethodViewModel> { PaymentMethodViewModelImpl(get()) }
}
