package com.coderwise.core.ui.permissions

import com.coderwise.core.permissions.Permission
import kotlinx.serialization.Serializable

data class PermissionsUiState(
    val location: PermissionUiState,
    val camera: PermissionUiState,
    val microphone: PermissionUiState,
    val storage: PermissionUiState,
    val notifications: PermissionUiState
) {
    companion object {
        fun initial() = PermissionsUiState(
            location = PermissionUiState("Location"),
            camera = PermissionUiState("Camera"),
            microphone = PermissionUiState("Microphone"),
            storage = PermissionUiState("Storage"),
            notifications = PermissionUiState("Notifications")
        )
    }
}

data class PermissionUiState(
    val name: String,
    val status: Permission.Status = Permission.Status.PENDING,
    val isLoading: Boolean = true
) {
    val isGranted get() = status == Permission.Status.GRANTED
    override fun toString(): String {
        return "$name: $status"
    }
}

sealed interface PermissionsAction {
    data object LocationPermissionClicked : PermissionsAction
    data object CameraPermissionClicked : PermissionsAction
    data object MicrophonePermissionClicked : PermissionsAction
    data object StoragePermissionClicked : PermissionsAction

    data object OnSettingsButtonClicked : PermissionsAction
    data object NotificationsPermissionClicked : PermissionsAction
}