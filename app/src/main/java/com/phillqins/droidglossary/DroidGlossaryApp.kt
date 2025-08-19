package com.phillqins.droidglossary

import android.app.Application
import com.phillqins.droidglossary.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class DroidGlossaryApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@DroidGlossaryApp)
            modules(appModules)
        }
    }
}