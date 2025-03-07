package com.coderwise.core.time

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter

class TimeBroadcastReceiver(
    private val onTimeChanged: () -> Unit
) : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_TIME_TICK) {
            onTimeChanged()
        }
    }

    companion object {
        val filter = IntentFilter(Intent.ACTION_TIME_TICK)
    }
}
