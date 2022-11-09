package com.saigon.compose.di.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.saigon.compose.data.local.SharePreferenceManager
import com.saigon.compose.ui.cart.CartViewModel
import com.saigon.compose.ui.cart.CartViewModelImpl
import com.saigon.compose.ui.login.LoginViewModel
import com.saigon.compose.ui.login.LoginViewModelImpl
import com.saigon.compose.ui.product_details.ProductDetailsViewModel
import com.saigon.compose.ui.product_details.ProductDetailsViewModelImpl
import com.saigon.compose.ui.shop.ShopViewModel
import com.saigon.compose.ui.shop.ShopViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { SharePreferenceManager.getInstance(get()) }

    single<Gson> {
        GsonBuilder()
            // .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
            .disableHtmlEscaping()
            .setPrettyPrinting()
            .create()
    }

    viewModel<LoginViewModel> { LoginViewModelImpl(get()) }
    viewModel<ShopViewModel> { ShopViewModelImpl(get()) }
    viewModel<ProductDetailsViewModel> { ProductDetailsViewModelImpl(get(), get()) }
    viewModel<CartViewModel> { CartViewModelImpl(get(), get()) }
}
