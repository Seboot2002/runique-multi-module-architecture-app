package com.sebascamayo.core.data.di

import OfflineFirstRunRepository
import com.sebascamayo.core.data.auth.EncryptedSessionStorage
import com.sebascamayo.core.data.networking.HttpClientFactory
import com.sebascamayo.core.domain.SessionStorage
import com.sebascamayo.core.domain.run.RunRepository
import io.ktor.client.HttpClient
import kotlinx.coroutines.runBlocking
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreDataModule = module {
    // Declaramos que es un SessionStorage junto con sus dependencias
    singleOf(::EncryptedSessionStorage).bind<SessionStorage>()

    //Creamos un singleton que sirve como dependencia
    single {
        val sessionStorage: SessionStorage = get()
        HttpClientFactory(sessionStorage).build()
    }

    //lazy{} hace que se cree solo cuando es necesario
    //El factory permite crear varias instancias diferente al single
    /*factory<HttpClient> {
        val sessionStorage: SessionStorage = get()
        lazy {
            HttpClientFactory(sessionStorage).build()
        }
    }*/

    singleOf(::OfflineFirstRunRepository).bind<RunRepository>()
}