package com.sebascamayo.run.data.di

import com.sebascamayo.core.domain.run.SyncRunScheduler
import com.sebascamayo.run.data.CreateRunWorker
import com.sebascamayo.run.data.DeleteRunWorker
import com.sebascamayo.run.data.FetchRunsWorker
import com.sebascamayo.run.data.SyncRunWorkerScheduler
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val runDataModule = module {

    workerOf(::CreateRunWorker)
    workerOf(::FetchRunsWorker)
    workerOf(::DeleteRunWorker)

    singleOf(::SyncRunWorkerScheduler).bind<SyncRunScheduler>()
}