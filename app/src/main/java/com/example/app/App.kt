package com.example.app

import android.app.Application
import com.example.app.di.databaseModule
import com.example.app.di.repositoryModule
import com.example.app.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(databaseModule, repositoryModule, viewModelModule)
        }
    }
}


