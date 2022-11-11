package com.saigon.compose

import android.app.Application
import com.google.firebase.FirebaseApp
import com.saigon.compose.data.firebase.firebaseModule
import com.saigon.compose.di.module.appModule
import com.saigon.compose.di.module.networkModule
import com.saigon.compose.di.module.repositoryModule
import com.saigon.compose.di.module.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        FirebaseApp.initializeApp(this).apply {
            Timber.d(this.toString() + "initializeApp Done")
        }

        GlobalContext.startKoin {
            androidLogger()
            androidContext(this@App)
            androidFileProperties()
            modules(appModule)
            modules(viewModelModule)
            modules(networkModule)
            modules(repositoryModule)
            modules(firebaseModule)
        }
    }
}
