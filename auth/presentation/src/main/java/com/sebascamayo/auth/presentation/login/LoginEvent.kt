package com.sebascamayo.auth.presentation.login

import com.sebascamayo.presentation.ui.UiText

sealed interface LoginEvent {

    data class Error(val error: UiText): LoginEvent
    data object LoginSuccess: LoginEvent
}