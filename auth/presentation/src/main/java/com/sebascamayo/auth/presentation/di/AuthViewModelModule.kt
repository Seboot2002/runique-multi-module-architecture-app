package com.sebascamayo.auth.presentation.di

import com.sebascamayo.auth.presentation.login.LoginViewModel
import com.sebascamayo.auth.presentation.register.RegisterViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val authViewModelModule = module {
    viewModelOf(::RegisterViewModel)
    viewModelOf(::LoginViewModel)
}