package com.coderwise.core.ui.arch

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
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
    private val snackbarHostStateProvider: SnackbarHostStateProvider
) : UiMessenger {
    override suspend fun showMessage(message: String) {
        snackbarHostStateProvider.get().showSnackbar(message)
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
