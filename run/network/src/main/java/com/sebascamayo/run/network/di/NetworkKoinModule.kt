package com.sebascamayo.run.network.di

import com.sebascamayo.core.domain.run.RemoteRunDataSource
import com.sebascamayo.run.network.KtorRemoteRunDataSource
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val networkModule = module {
    singleOf(::KtorRemoteRunDataSource).bind<RemoteRunDataSource>()
}