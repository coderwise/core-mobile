package com.coderwise.core.ui.permissions

import kotlinx.serialization.Serializable

@Serializable
data object Permissions

data class PermissionsUiState(
    val location: PermissionUiState,
    val camera: PermissionUiState,
    val microphone: PermissionUiState,
    val storage: PermissionUiState
) {
    companion object {
        fun initial() = PermissionsUiState(
            location = PermissionUiState("Location", false, true),
            camera = PermissionUiState("Camera", false, true),
            microphone = PermissionUiState("Microphone", false, true),
            storage = PermissionUiState("Storage", false, true)
        )
    }
}

data class PermissionUiState(
    val name: String,
    val isGranted: Boolean,
    val isLoading: Boolean
) {
    override fun toString(): String {
        return "$name: $isGranted"
    }
}

sealed interface PermissionsAction {
    object LocationPermissionClicked : PermissionsAction
    object CameraPermissionClicked : PermissionsAction
    object MicrophonePermissionClicked : PermissionsAction
    object StoragePermissionClicked : PermissionsAction
}