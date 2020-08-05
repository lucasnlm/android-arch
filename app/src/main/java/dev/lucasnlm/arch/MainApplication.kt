package dev.lucasnlm.arch

import android.app.Application
import dev.lucasnlm.arch.info.di.DataModule
import dev.lucasnlm.arch.info.di.DeviceInfoModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@ExperimentalCoroutinesApi
@FlowPreview
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            modules(DeviceInfoModule, DataModule)
        }
    }
}
