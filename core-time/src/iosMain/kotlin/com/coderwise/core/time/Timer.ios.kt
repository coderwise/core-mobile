package com.coderwise.core.time

import platform.Foundation.NSDate
import platform.Foundation.NSRunLoop
import platform.Foundation.NSRunLoopCommonModes
import platform.Foundation.NSTimer

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class Timer actual constructor(
    private val tag: String?,
    private val intervalMillis: Long,
    private val onTick: () -> Unit
) {
    private var timer: NSTimer? = null

    actual fun start() {
        timer = NSTimer(
            fireDate = NSDate(
                NSDate().timeIntervalSinceReferenceDate / 1000
            ),
            interval = intervalMillis.toDouble() / 1000,
            repeats = true,
            block = { onTick() }
        )
        timer?.let {
            NSRunLoop.currentRunLoop().addTimer(it, NSRunLoopCommonModes)
        }
    }

    actual fun cancel() {
        timer?.invalidate()
        timer = null
    }
}