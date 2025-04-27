package com.coderwise.core.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.coderwise.core.ui.utils.CorePreview
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CorePropertyText(
    text: String,
    modifier: Modifier = Modifier,
    label: String? = null
) {
    Column(modifier = modifier) {
        label?.let {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview
@Composable
private fun CorePropertyTextPreview() {
    CorePreview {
        CorePropertyText(
            text = "Hello",
            label = "Label",
            modifier = Modifier.fillMaxWidth()
        )
    }
}