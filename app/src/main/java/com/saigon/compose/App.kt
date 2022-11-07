package com.saigon.compose

import android.app.Application
import com.saigon.compose.data.firebase.firebaseModule
import com.saigon.compose.di.module.appModule
import com.saigon.compose.di.module.networkModule
import com.saigon.compose.di.module.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext
import timber.log.Timber

class App : Application(){
    override fun onCreate() {
        super.onCreate()
        GlobalContext.startKoin {
            androidLogger()
            androidContext(this@App)
            androidFileProperties()
            modules(appModule)
            modules(networkModule)
            modules(repositoryModule)
            modules(firebaseModule)
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
