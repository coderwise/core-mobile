package com.coderwise.core.time

import kotlinx.coroutines.flow.Flow
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
interface TimeService {
    fun time(interval: Duration): Flow<Instant>

    fun now(): Instant
}