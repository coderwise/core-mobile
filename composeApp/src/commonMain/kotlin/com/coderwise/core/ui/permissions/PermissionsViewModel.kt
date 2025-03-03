package com.coderwise.core.ui.permissions

import com.coderwise.core.ui.arch.BaseViewModel
import com.coderwise.core.ui.arch.NavigationRouter
import com.coderwise.permissions.CAMERA
import com.coderwise.permissions.LOCATION
import com.coderwise.permissions.MICROPHONE
import com.coderwise.permissions.Permission
import com.coderwise.permissions.PermissionService
import com.coderwise.permissions.STORAGE

class PermissionsViewModel(
    private val navigationRouter: NavigationRouter,
    private val permissionService: PermissionService
) : BaseViewModel<PermissionsUiState, PermissionsUiState>(
    initialState = PermissionsUiState.initial(),
    mapper = { it }
) {
    init {
        asyncAction {
            permissionService.statusFlow(Permission.LOCATION).collect {
                reduce { copy(location = location.copy(isGranted = it == Permission.Status.GRANTED)) }
            }
        }
        asyncAction {
            permissionService.statusFlow(Permission.CAMERA).collect {
                reduce { copy(camera = camera.copy(isGranted = it == Permission.Status.GRANTED)) }
            }
        }
        asyncAction {
            permissionService.statusFlow(Permission.MICROPHONE).collect {
                reduce { copy(microphone = microphone.copy(isGranted = it == Permission.Status.GRANTED)) }
            }
        }
        asyncAction {
            permissionService.statusFlow(Permission.STORAGE).collect {
                reduce { copy(storage = storage.copy(isGranted = it == Permission.Status.GRANTED)) }
            }
        }
    }

    override fun onAction(action: Any) {
        when (action) {
            is PermissionsAction.LocationPermissionClicked -> {
                asyncAction {
                    permissionService.requestPermission(Permission.LOCATION)
                }
            }

            is PermissionsAction.CameraPermissionClicked -> {
                asyncAction {
                    permissionService.requestPermission(Permission.CAMERA)
                }
            }

            is PermissionsAction.MicrophonePermissionClicked -> {
                asyncAction {
                    permissionService.requestPermission(Permission.MICROPHONE)
                }
            }

            is PermissionsAction.StoragePermissionClicked -> {
                asyncAction {
                    permissionService.requestPermission(Permission.STORAGE)
                }
            }

            else -> {
                println("Unknown action: $action")
            }
        }
    }
}
