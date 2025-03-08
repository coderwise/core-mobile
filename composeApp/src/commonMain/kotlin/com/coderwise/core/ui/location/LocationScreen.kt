package com.coderwise.core.ui.location

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.coderwise.core.ui.theme.CorePreview
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LocationScreen(
    viewModel: LocationViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LocationContent(
        uiState
    )
}

@Composable
internal fun LocationContent(
    uiState: Unit
) {

}

@Preview
@Composable
fun LocationPreview() {
    CorePreview {
        Text("test")
    }
}