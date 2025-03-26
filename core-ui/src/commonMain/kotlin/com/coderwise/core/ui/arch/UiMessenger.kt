package com.coderwise.core.ui.arch

import androidx.compose.material3.SnackbarDuration

data class UiNotification(
    val message: String,
    val actionLabel: String? = null,
    val withDismissAction: Boolean = false,
    val duration: SnackbarDuration = SnackbarDuration.Short
)

interface UiMessenger {
    fun showNotification(notification: UiNotification)

    fun clearNotifications()
}