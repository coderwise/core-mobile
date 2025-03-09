package com.coderwise.core.ui.permissions

import kotlinx.serialization.Serializable

@Serializable
data object PermissionsRoute

data class PermissionsUiState(
    val location: PermissionUiState,
    val camera: PermissionUiState,
    val microphone: PermissionUiState,
    val storage: PermissionUiState
) {
    companion object {
        fun initial() = PermissionsUiState(
            location = PermissionUiState("Location"),
            camera = PermissionUiState("Camera"),
            microphone = PermissionUiState("Microphone"),
            storage = PermissionUiState("Storage")
        )
    }
}

data class PermissionUiState(
    val name: String,
    val isGranted: Boolean = false,
    val isLoading: Boolean = true
) {
    override fun toString(): String {
        return "$name: ${if (isGranted) "Granted" else "Denied"}"
    }
}

sealed interface PermissionsAction {
    data object LocationPermissionClicked : PermissionsAction
    data object CameraPermissionClicked : PermissionsAction
    data object MicrophonePermissionClicked : PermissionsAction
    data object StoragePermissionClicked : PermissionsAction
}