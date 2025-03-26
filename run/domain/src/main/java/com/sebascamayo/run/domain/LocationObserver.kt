package com.sebascamayo.run.domain

import com.sebascamayo.core.domain.location.LocationWithAltitude
import kotlinx.coroutines.flow.Flow

interface LocationObserver {
    fun obseveLocation(interval: Long): Flow<LocationWithAltitude>
}