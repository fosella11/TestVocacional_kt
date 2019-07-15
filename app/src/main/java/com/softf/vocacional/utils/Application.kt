package com.softf.vocacional.utils

import android.app.Application
import android.provider.FontsContract
import androidx.core.provider.FontRequest
import com.softf.vocacional.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@Application)
            modules(appModule)
        }
    }
}