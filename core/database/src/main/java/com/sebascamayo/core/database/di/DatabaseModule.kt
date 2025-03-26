package com.sebascamayo.core.database.di

import androidx.room.Room
import com.sebascamayo.core.database.RoomLocalRunDataSource
import com.sebascamayo.core.database.RunDatabase
import com.sebascamayo.core.domain.run.LocalRunDataSource
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val databaseModule = module {

    // Singleton de database
    single {
        Room.databaseBuilder(
            context = androidApplication(),
            klass = RunDatabase::class.java,
            name = "run.db"
        ).build()
    }

    // Singleton del Dao
    single { get<RunDatabase>().runDao }
    single { get<RunDatabase>().runPendingSyncDao }

    singleOf(::RoomLocalRunDataSource).bind<LocalRunDataSource>()
}