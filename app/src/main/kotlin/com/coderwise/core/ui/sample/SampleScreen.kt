package com.coderwise.core.ui.sample

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.coderwise.core.ui.theme.Core_LibraryTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun SampleScreen(
    viewModel: SampleViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    SampleContent(
        uiState = uiState,
        onAction = viewModel::onAction
    )
}

@Composable
private fun SampleContent(
    uiState: SampleUiState,
    onAction: (SampleAction) -> Unit
) {
    Column {
        Text(text = uiState.test, modifier = Modifier.padding(16.dp))

        Button(
            onClick = { onAction(SampleAction.ButtonClicked) },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Click me")
        }
    }
}

@PreviewLightDark
@Composable
private fun SamplePreview() {
    Core_LibraryTheme {
        Surface {
            SampleContent(
                uiState = SampleUiState("Test"),
                onAction = {}
            )
        }
    }
}