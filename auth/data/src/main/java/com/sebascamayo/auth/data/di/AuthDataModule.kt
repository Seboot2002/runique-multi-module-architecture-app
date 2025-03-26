package com.sebascamayo.auth.data.di

import com.sebascamayo.auth.data.AuthRepositoryImpl
import com.sebascamayo.auth.data.EmailPatternValidator
import com.sebascamayo.auth.domain.AuthRepository
import com.sebascamayo.auth.domain.PatternValidator
import com.sebascamayo.auth.domain.UserDataValidator
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val authDataModule = module {
    single<PatternValidator> {
        EmailPatternValidator
    }
    singleOf(::UserDataValidator)
    singleOf(::AuthRepositoryImpl).bind<AuthRepository>()
}
