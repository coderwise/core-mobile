package com.coderwise.core.ui.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

fun Context.openPage(
    action: String,
    uri: Uri? = null
) {
    try {
        val intent = Intent(action).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            uri?.let {
                data = uri
            }
        }
        startActivity(intent)
    } catch (e: Exception) {
        // NOOP
    }
}