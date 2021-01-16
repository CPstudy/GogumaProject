package com.cpstudy.gogumaproject

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GogumaApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@GogumaApplication)
            modules(appModules)
        }
    }
}