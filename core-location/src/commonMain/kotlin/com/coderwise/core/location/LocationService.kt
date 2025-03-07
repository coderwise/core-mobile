package com.coderwise.core.location

import kotlinx.coroutines.flow.Flow

interface LocationService {
    val coordinates: Flow<LatLon>

    val status: Flow<Status>

    enum class Status {
        STARTED,
        STOPPED,
        ERROR
    }
}