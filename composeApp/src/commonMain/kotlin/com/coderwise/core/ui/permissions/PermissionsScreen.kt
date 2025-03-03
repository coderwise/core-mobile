package com.coderwise.core.ui.permissions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.coderwise.core.ui.theme.CorePreview
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp

@Composable
fun PermissionsScreen(
    viewModel: PermissionsViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    PermissionsContent(
        uiState = uiState,
        onAction = viewModel::dispatch
    )
}

@Composable
private fun PermissionsContent(
    uiState: PermissionsUiState,
    onAction: (PermissionsAction) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        PermissionItem(
            uiState.location,
            { onAction(PermissionsAction.LocationPermissionClicked) })
        PermissionItem(
            uiState.camera,
            { onAction(PermissionsAction.CameraPermissionClicked) })
        PermissionItem(
            uiState.microphone,
            { onAction(PermissionsAction.MicrophonePermissionClicked) })
        PermissionItem(
            uiState.storage,
            { onAction(PermissionsAction.StoragePermissionClicked) })
    }
}

@Composable
private fun PermissionItem(
    permissionState: PermissionUiState,
    onButtonClicked: () -> Unit
) {
    Row(
        modifier = Modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = permissionState.toString(),
            modifier = Modifier.weight(1f)
        )
        Button(
            onClick = onButtonClicked,
        ) {
            Text(text = "Request")
        }
    }
}

@Preview
@Composable
private fun PermissionsScreenPreview() {
    CorePreview {
        PermissionsContent(
            uiState = PermissionsUiState.initial()
        ) {}
    }
}