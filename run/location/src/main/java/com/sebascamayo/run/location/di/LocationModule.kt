package com.sebascamayo.run.location.di

import com.sebascamayo.run.domain.LocationObserver
import com.sebascamayo.run.location.AndroidLocationObserver
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val locationModule = module {

    singleOf(::AndroidLocationObserver).bind<LocationObserver>()
}