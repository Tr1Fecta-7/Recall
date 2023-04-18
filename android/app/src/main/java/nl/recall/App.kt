package nl.recall

import android.app.Application
import nl.recall.generic.di.appModule
import nl.recall.generic.di.dataModule
import nl.recall.generic.di.domainModule
import nl.recall.generic.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin

class App : Application(), KoinComponent {



    override fun onCreate() {
        super.onCreate()
        startKoin()
    }

    private fun startKoin() {
        startKoin {
            androidContext(this@App)
            modules(appModule, presentationModule, dataModule, domainModule)
        }
    }
}
