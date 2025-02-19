package com.coderwise.core.ui.sample.edit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
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
import org.koin.core.parameter.parametersOf

@Composable
fun EditScreen(
    args: EditRoute,
    viewModel: EditViewModel = koinViewModel { parametersOf(args.id) }
) {
    val uiState by viewModel.uiState.collectAsState()
    EditContent(
        uiState = uiState,
        dispatch = viewModel::dispatch
    )
}

@Composable
private fun EditContent(
    uiState: EditUiState,
    dispatch: (EditAction) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        OutlinedTextField(
            value = uiState.sampleValue,
            onValueChange = { dispatch(EditAction.ValueUpdated(it)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        Button(
            onClick = { dispatch(EditAction.Save) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            enabled = uiState.saveEnabled
        ) {
            Text("Save")
        }
    }
}

@PreviewLightDark
@Composable
private fun EditPreview() {
    Core_LibraryTheme {
        Surface {
            EditContent(
                uiState = EditUiState(
                    sampleValue = "test",
                    saveEnabled = true,
                    valueEnabled = true
                )
            ) {}
        }
    }
}