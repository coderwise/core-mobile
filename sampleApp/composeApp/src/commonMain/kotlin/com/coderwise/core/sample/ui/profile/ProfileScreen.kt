package com.coderwise.core.sample.ui.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.coderwise.core.sample.ui.theme.ScreenPreview
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProfileScreen(viewModel: ProfileViewModel = koinViewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    ProfileContent(uiState, viewModel::dispatch)
}

@Composable
fun ProfileContent(
    uiState: ProfileUiState,
    dispatch: (ProfileAction) -> Unit
) {
    Column(
        modifier = Modifier.padding(16.dp).fillMaxSize()
    ) {
        Text(uiState.name)
    }
}

@Preview
@Composable
fun ProfileContentPreview() {
    ScreenPreview {
        val uiState = ProfileUiState(name = "John")
        ProfileContent(
            uiState
        ) {}
    }
}

