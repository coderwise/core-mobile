package com.coderwise.core.ui.theme

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CorePreview(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Core_LibraryTheme {
        Surface(modifier = modifier) {
            content()
        }
    }
}

@Composable
fun ScreenPreview(
    content: @Composable () -> Unit
) {
    CorePreview(
        modifier = Modifier.fillMaxSize(),
        content = content
    )
}

@Preview
@Composable
private fun ScreenPreviewPreview() {
    ScreenPreview {
        Text(text = "text")
    }
}
