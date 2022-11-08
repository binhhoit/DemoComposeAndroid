package com.saigon.compose.di.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.saigon.compose.data.local.SharePreferenceManager
import com.saigon.compose.ui.login.LoginViewModel
import com.saigon.compose.ui.login.LoginViewModelImpl
import com.saigon.compose.ui.shop.ShopViewModel
import com.saigon.compose.ui.shop.ShopViewModelImpl
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

    single<LoginViewModel> { LoginViewModelImpl(get()) }
    single<ShopViewModel> { ShopViewModelImpl(get()) }
}
