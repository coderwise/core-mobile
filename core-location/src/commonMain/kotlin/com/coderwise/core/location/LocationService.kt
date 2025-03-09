package com.coderwise.core.location

import kotlinx.coroutines.flow.Flow
import kotlin.time.Duration

interface LocationService {
    suspend fun start()

    suspend fun stop()

    suspend fun configure(minTime: Duration, minDistance: Float)

    val gpsMessages: Flow<GpsMessage>

    val status: Flow<Status>

    enum class Status {
        STARTING,
        STARTED,
        STOPPING,
        STOPPED,
        ERROR
    }

    data class Configuration(
        val minTime: Duration,
        val minDistance: Float
    )
}