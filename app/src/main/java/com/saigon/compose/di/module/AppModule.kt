package com.saigon.compose.di.module

import org.koin.dsl.module

val appModule = module {
  /*  single { SharePreferenceManager.getInstance(get()) }

    single<Gson> {
        GsonBuilder()
            // .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
            .setDateFormat(API_DATE_TIME_FORMAT)
            .disableHtmlEscaping()
            .setPrettyPrinting()
            .create()
    }*/
}
