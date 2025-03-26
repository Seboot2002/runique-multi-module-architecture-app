package com.sebascamayo.core.data.di

import OfflineFirstRunRepository
import com.sebascamayo.core.data.auth.EncryptedSessionStorage
import com.sebascamayo.core.data.networking.HttpClientFactory
import com.sebascamayo.core.domain.SessionStorage
import com.sebascamayo.core.domain.run.RunRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreDataModule = module {
    //Obtiene las dependencias automaticamente
    single {
        HttpClientFactory(get()).build()
    }
    // Declaramos que es un SessionStorage
    singleOf(::EncryptedSessionStorage).bind<SessionStorage>()

    singleOf(::OfflineFirstRunRepository).bind<RunRepository>()
}