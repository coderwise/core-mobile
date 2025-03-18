package com.coderwise.core.time

import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Instant
import kotlin.time.Duration

interface TimeService {
    fun time(interval: Duration): Flow<Instant>

    fun now(): Instant
}