package com.example.unihub

import android.app.Application
import com.example.unihub.di.appModule
import com.example.unihub.di.authModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class UniclubApp : Application() {
    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        startKoin {
            androidLogger()
            androidContext(applicationContext)
            modules(appModule)
            modules(authModule)
        }
    }
}