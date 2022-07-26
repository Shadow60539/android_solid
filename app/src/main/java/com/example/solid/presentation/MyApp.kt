package com.example.solid.presentation

import android.app.Application
import com.example.solid.core.di.appModule
//import dagger.hilt.android.HiltAndroidApp
import org.koin.core.context.startKoin

//@HiltAndroidApp()
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(appModule)
        }
    }
}