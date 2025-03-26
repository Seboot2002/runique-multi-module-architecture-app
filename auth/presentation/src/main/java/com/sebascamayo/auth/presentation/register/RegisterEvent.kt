package com.sebascamayo.auth.presentation.register

import com.sebascamayo.presentation.ui.UiText

sealed interface RegisterEvent {

    data object RegistrationSuccess: RegisterEvent
    data class Error(val error: UiText): RegisterEvent
}