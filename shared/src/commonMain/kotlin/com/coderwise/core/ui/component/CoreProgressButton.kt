package com.coderwise.core.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CoreProgressButton(
    text: String,
    modifier: Modifier = Modifier,
    isProgress: Boolean = false,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled && !isProgress
    ) {
        if (isProgress) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .size(24.dp)
            )
        } else {
            Text(text)
        }
    }
}

@Preview
@Composable
private fun CoreProgressButtonPreview() {
    MaterialTheme {
        Surface {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                CoreProgressButton(
                    text = "Button",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {}
                CoreProgressButton(
                    text = "Progress",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    isProgress = true
                ) {}
            }
        }
    }
}