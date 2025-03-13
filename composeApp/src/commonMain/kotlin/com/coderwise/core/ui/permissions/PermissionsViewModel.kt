package com.coderwise.core.ui.permissions

import com.coderwise.core.permissions.CAMERA
import com.coderwise.core.permissions.LOCATION
import com.coderwise.core.permissions.MICROPHONE
import com.coderwise.core.permissions.NOTIFICATIONS
import com.coderwise.core.permissions.Permission
import com.coderwise.core.permissions.PermissionService
import com.coderwise.core.permissions.STORAGE
import com.coderwise.core.ui.OsSettingsService
import com.coderwise.core.ui.arch.BaseViewModel

class PermissionsViewModel(
    private val permissionService: PermissionService,
    private val settingsService: OsSettingsService
) : BaseViewModel<PermissionsUiState, PermissionsUiState>(
    initialState = PermissionsUiState.initial(),
    mapper = { it }
) {
    init {
        asyncAction {
            permissionService.statusFlow(Permission.LOCATION).collect {
                reduce { copy(location = location.copy(status = it)) }
            }
        }
        asyncAction {
            permissionService.statusFlow(Permission.CAMERA).collect {
                reduce { copy(camera = camera.copy(status = it)) }
            }
        }
//        asyncAction {
//            permissionService.statusFlow(Permission.MICROPHONE).collect {
//                reduce { copy(microphone = microphone.copy(status = it)) }
//            }
//        }
//        asyncAction {
//            permissionService.statusFlow(Permission.STORAGE).collect {
//                reduce { copy(storage = storage.copy(status = it)) }
//            }
//        }
//        asyncAction {
//            permissionService.statusFlow(Permission.NOTIFICATIONS).collect {
//                reduce { copy(notifications = notifications.copy(status = it)) }
//            }
//        }
    }

    override fun onAction(action: Any) {
        when (action) {
            is PermissionsAction.LocationPermissionClicked -> asyncAction {
                permissionService.requestPermission(Permission.LOCATION)
            }

            is PermissionsAction.CameraPermissionClicked -> asyncAction {
                permissionService.requestPermission(Permission.CAMERA)
            }

            is PermissionsAction.MicrophonePermissionClicked -> asyncAction {
                permissionService.requestPermission(Permission.MICROPHONE)
            }

            is PermissionsAction.StoragePermissionClicked -> asyncAction {
                permissionService.requestPermission(Permission.STORAGE)
            }

            is PermissionsAction.NotificationsPermissionClicked -> asyncAction {
                permissionService.requestPermission(Permission.NOTIFICATIONS)
            }

            is PermissionsAction.OnSettingsButtonClicked -> asyncAction {
                settingsService.openSettings()
            }

            else -> {
                println("Unknown action: $action")
            }
        }
    }
}
