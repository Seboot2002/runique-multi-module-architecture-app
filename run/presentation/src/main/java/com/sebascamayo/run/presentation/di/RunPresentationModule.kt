package com.sebascamayo.run.presentation.di

import com.sebascamayo.run.domain.RunningTracker
import com.sebascamayo.run.presentation.active_run.ActiveRunViewModel
import com.sebascamayo.run.presentation.run_overview.RunOverviewViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val runPresentationModule = module {
    singleOf(::RunningTracker)

    viewModelOf(::RunOverviewViewModel)
    viewModelOf(::ActiveRunViewModel)
}