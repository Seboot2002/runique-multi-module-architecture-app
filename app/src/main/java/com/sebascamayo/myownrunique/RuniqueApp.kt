package com.sebascamayo.myownrunique

import android.app.Application
import android.content.Context
import com.sebascamayo.auth.data.di.authDataModule
import com.sebascamayo.auth.presentation.di.authViewModelModule
import com.sebascamayo.core.data.di.coreDataModule
import com.sebascamayo.run.network.di.networkModule
import com.sebascamayo.core.database.di.databaseModule
import com.sebascamayo.myownrunique.di.appModule
import com.sebascamayo.run.location.di.locationModule
import com.sebascamayo.run.presentation.di.runPresentationModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.startKoin
import timber.log.Timber
import com.google.android.play.core.splitcompat.SplitCompat
import com.sebascamayo.run.data.di.runDataModule

// Aqui va a empezar a correr la aplicacion

class RuniqueApp: Application() {

    // Creamos un hilo de tipo SupervisorJob que nos da un mayor control que el Job
    val applicationScope = CoroutineScope(SupervisorJob())

    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidLogger()
            androidContext(this@RuniqueApp)
            //workManagerFactory()
            modules(
                authDataModule,
                authViewModelModule,
                appModule,
                coreDataModule,
                locationModule,
                runPresentationModule,
                databaseModule,
                networkModule,
                runDataModule
            )
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        SplitCompat.install(this)
    }
}