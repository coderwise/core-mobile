package com.coderwise.core.time

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class Timer(
    tag: String? = null,
    intervalMillis: Long,
    onTick: () -> Unit
) {
    fun start()

    fun cancel()
}