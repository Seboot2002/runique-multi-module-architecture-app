package com.sebascamayo.run.domain

import com.sebascamayo.core.domain.Timer
import com.sebascamayo.core.domain.location.LocationTimestamp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combineTransform
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.zip
import kotlin.math.roundToInt
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

class RunningTracker(
    private val locationObserver: LocationObserver,
    private val applicationScope: CoroutineScope
) {
    private val _runData = MutableStateFlow(RunData())
    val runData = _runData.asStateFlow()

    val isTracking = MutableStateFlow(false)
    private val isObservingLocation = MutableStateFlow(false)

    private val _elapsedTime = MutableStateFlow(Duration.ZERO)
    val elapsedTime = _elapsedTime.asStateFlow()

    val currentLocation = isObservingLocation
        .flatMapLatest { isObservingLocation ->

            // Usamos el LocationObserver
            // Vemos si el ultimo valor boolean es true
            if(isObservingLocation) {
                locationObserver.obseveLocation(1000L)
            } else flowOf()
        }
        .stateIn(
            applicationScope, //CoroutineScope(Dispatchers.Main),
            SharingStarted.Lazily,
            null
        )

    init {
        isTracking
            .onEach { isTracking ->
                //Al pausar creamos una lista vacia que sirve como intermedio para otro run
                if(!isTracking) {
                    val newList: List<List<LocationTimestamp>> = buildList {
                        addAll(runData.value.locations)
                        add(emptyList<LocationTimestamp>())
                    }.toList()
                    _runData.update {
                        it.copy(
                            locations = newList
                        )
                    }
                }
            }
            .flatMapLatest { isTrackingVal ->
                if (isTrackingVal) {
                    Timer.timeAndEmit()
                } else flowOf()
            }
            .onEach {
                _elapsedTime.value += it
            }
            .launchIn(applicationScope)

        // combine() obtiene los ultimos valores de varios flows
        // combineTransform() permite emitir en cualquier momento o en alguna condicion

        // zip() permite combinar dos listas de elementos y cada uno en tuplas en orden
        // Ejem 1,2 | a,b -> (1,a),(2,b)
        currentLocation
            .filterNotNull()
            .combineTransform(isTracking) { location, isTracking ->

                if(isTracking) {
                    emit(location)
                }
            }.zip(_elapsedTime) { location, elapsedTime ->
                 LocationTimestamp(
                     location = location,
                     durationTimestamp = elapsedTime
                 )
            }.onEach { locationWithTime ->
                val currentLocations = runData.value.locations
                val lastLocationsList = if(currentLocations.isNotEmpty()) {
                     currentLocations.last() + locationWithTime
                } else listOf(locationWithTime)
                val newLocationsList = currentLocations.replaceLast(lastLocationsList)

                val distanceMeters = LocationDataCalculator.getTotalDistanceMeters(
                    locations = newLocationsList
                )
                val distanceKm = distanceMeters / 1000.0
                val currentDuration = locationWithTime.durationTimestamp

                val avgSecondsPerKm = if(distanceKm == 0.0) {
                    0
                } else {
                    (currentDuration.inWholeSeconds / distanceKm).roundToInt()
                }

                _runData.update {
                    RunData(
                        distanceMeters = distanceMeters,
                        pace = avgSecondsPerKm.seconds,
                        locations = newLocationsList
                    )
                }
            }.launchIn(applicationScope)
    }

    fun setIsTracking(isTracking: Boolean) {
        this.isTracking.value = isTracking
    }

    fun startObservingLocation() {
        isObservingLocation.value = true
    }

    fun stopObservingLocation() {
        isObservingLocation.value = false
    }

    fun finishRun() {
        stopObservingLocation()
        setIsTracking(true)
        _elapsedTime.value = Duration.ZERO
        _runData.value = RunData()
    }

}

private fun <T> List<List<T>>.replaceLast(replacement: List<T>): List<List<T>> {
    if(this.isEmpty()) {
        return listOf(replacement)
    }
    return this.dropLast(1) + listOf(replacement)
}