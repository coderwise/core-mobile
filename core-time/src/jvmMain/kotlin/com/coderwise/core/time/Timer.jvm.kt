package com.coderwise.core.time

import java.util.Timer
import kotlin.concurrent.fixedRateTimer

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class Timer actual constructor(
    private val tag: String?,
    private val intervalMillis: Long,
    private val onTick: () -> Unit
) {
    private var timer: Timer? = null

    actual fun start() {
        timer = fixedRateTimer(
            name = tag,
            initialDelay = 0,
            period = intervalMillis
        ) {
            onTick()
        }
    }

    actual fun cancel() {
        timer?.cancel()
        timer = null
    }
}