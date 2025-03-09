package com.coderwise.core.ui.permissions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.coderwise.core.ui.component.BoxyRow
import com.coderwise.core.ui.theme.CorePreview
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun PermissionsScreen(
    viewModel: PermissionsViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    PermissionsContent(
        uiState = uiState, onAction = viewModel::dispatch
    )
}

@Composable
internal fun PermissionsContent(
    uiState: PermissionsUiState, onAction: (PermissionsAction) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        PermissionItem(
            permissionState = uiState.location,
            onRequestButtonClicked = { onAction(PermissionsAction.LocationPermissionClicked) },
            onSettingsButtonClicked = { onAction(PermissionsAction.OnSettingsButtonClicked) })
        PermissionItem(
            permissionState = uiState.camera,
            onRequestButtonClicked = { onAction(PermissionsAction.CameraPermissionClicked) },
            onSettingsButtonClicked = { onAction(PermissionsAction.OnSettingsButtonClicked) })
        PermissionItem(
            permissionState = uiState.microphone,
            onRequestButtonClicked = { onAction(PermissionsAction.MicrophonePermissionClicked) },
            onSettingsButtonClicked = { onAction(PermissionsAction.OnSettingsButtonClicked) })
        PermissionItem(
            permissionState = uiState.storage,
            onRequestButtonClicked = { onAction(PermissionsAction.StoragePermissionClicked) },
            onSettingsButtonClicked = { onAction(PermissionsAction.OnSettingsButtonClicked) })
    }
}

@Composable
private fun PermissionItem(
    permissionState: PermissionUiState,
    onRequestButtonClicked: () -> Unit,
    onSettingsButtonClicked: () -> Unit
) {
    BoxyRow(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = permissionState.toString()
        )
        Row {
            Button(
                onClick = onRequestButtonClicked,
                modifier = Modifier.padding(end = 8.dp),
                enabled = !permissionState.isGranted
            ) {
                Text(text = "Request")
            }
            Button(
                onClick = onSettingsButtonClicked
            ) {
                Text(text = "Settings")
            }
        }
    }
}

@Preview
@Composable
internal fun PermissionsPreview() {
    CorePreview {
        PermissionsContent(
            uiState = PermissionsUiState.initial()
        ) {}
    }
}