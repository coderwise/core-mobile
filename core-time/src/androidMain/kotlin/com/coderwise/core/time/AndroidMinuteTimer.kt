package com.coderwise.core.time

import android.content.Context
import androidx.core.content.ContextCompat

/**
 * TODO use this timer when duration is 1.minutes
 */
class AndroidMinuteTimer(
    private val context: Context,
    onTick: () -> Unit
) {
    val broadcastReceiver = TimeBroadcastReceiver {
        onTick()
    }

    fun start() {
        ContextCompat.registerReceiver(
            context,
            broadcastReceiver,
            TimeBroadcastReceiver.filter,
            ContextCompat.RECEIVER_EXPORTED
        )
    }

    fun cancel() {
        context.unregisterReceiver(broadcastReceiver)
    }
}