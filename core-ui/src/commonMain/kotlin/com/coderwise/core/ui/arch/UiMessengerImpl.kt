package com.coderwise.core.ui.arch

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

class SnackbarHostStateProvider {
    private lateinit var snackbarHostState: SnackbarHostState

    fun get() = snackbarHostState

    fun reset(snackbarHostState: SnackbarHostState) {
        this.snackbarHostState = snackbarHostState
    }
}

@Stable
class UiMessengerImpl(
    private val snackbarHostStateProvider: SnackbarHostStateProvider,
    private val scope: CoroutineScope
) : UiMessenger {
    override fun showNotification(notification: UiNotification) {
        scope.launch {
            snackbarHostStateProvider.get().showSnackbar(
                message = notification.message,
                actionLabel = notification.actionLabel,
                withDismissAction = notification.withDismissAction,
                duration = notification.duration
            )
        }
    }
}

@Composable
fun rememberUiMessenger(
    snackbarHostState: SnackbarHostState
): UiMessenger {
    koinInject<SnackbarHostStateProvider>().apply {
        reset(snackbarHostState)
    }
    val uiMessenger = koinInject<UiMessenger>()
    return remember { uiMessenger }
}
