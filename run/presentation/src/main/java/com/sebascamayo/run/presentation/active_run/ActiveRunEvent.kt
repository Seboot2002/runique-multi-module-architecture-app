package com.sebascamayo.run.presentation.active_run

import com.sebascamayo.presentation.ui.UiText

sealed interface ActiveRunEvent {

    data class Error(val error: UiText): ActiveRunEvent
    data object RunSaved: ActiveRunEvent
}