package com.johnny.fagundes.spaceexplorer

import android.app.Application
import com.johnny.fagundes.spaceexplorer.di.appModule
import com.johnny.fagundes.spaceexplorer.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class SpaceExplorerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Start Koin
        startKoin {
            androidLogger()
            androidContext(this@SpaceExplorerApplication)
            modules(listOf(appModule, networkModule))
        }
    }
}