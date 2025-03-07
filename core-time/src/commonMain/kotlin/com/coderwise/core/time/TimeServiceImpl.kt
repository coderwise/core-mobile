package com.coderwise.core.time

import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.time.Duration

class TimeServiceImpl(
    private val clock: Clock,
) : TimeService {

    override fun time(interval: Duration): Flow<Instant> = callbackFlow {
        val timer = Timer(
            intervalMillis = interval.inWholeMilliseconds,
            onTick = { trySend(clock.now()) }
        )

        timer.start()

        awaitClose { timer.cancel() }
    }
}