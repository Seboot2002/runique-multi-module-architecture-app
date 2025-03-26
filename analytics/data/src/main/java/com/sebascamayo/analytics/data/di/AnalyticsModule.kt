package com.sebascamayo.analytics.data.di

import com.sebascamayo.analytics.data.RoomAnalyticsRepository
import com.sebascamayo.analytics.domain.AnalyticsRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val analyticsModule = module {

    singleOf(::RoomAnalyticsRepository).bind<AnalyticsRepository>()
}