package com.coderwise.core.sample.ui.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.coderwise.core.sample.ui.theme.ScreenPreview
import com.coderwise.core.ui.component.CoreProgressButton
import com.coderwise.core.ui.component.CorePropertyText
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
        CorePropertyText(
            text = uiState.name,
            modifier = Modifier.padding(bottom = 16.dp),
            label = "Name"
        )

        CoreProgressButton(
            text = "Logout",
            onClick = { dispatch(ProfileAction.Logout) },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )
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

