package com.nfd.hovering

import android.app.Application
import com.nfd.hovering.viewmodels.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

class MainApplication : Application() {
    override fun onCreate(){
        super.onCreate()

        startKoin {
            // declare used Android context
            androidContext(this@MainApplication)
            // declare modules
            modules(myModule)
        }
    }
}

// declared ViewModel using the viewModel keyword
val myModule : Module = module {
    viewModel { MainViewModel(get()) }
}
