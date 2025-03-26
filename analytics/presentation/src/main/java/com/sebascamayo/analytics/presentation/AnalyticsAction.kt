package com.sebascamayo.analytics.presentation

sealed interface AnalyticsAction {

    data object OnBackClick: AnalyticsAction
}