package com.coderwise.core.ui.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun DebugBox(
    content: @Composable () -> Unit
) {
    var size by remember { mutableStateOf(IntSize.Zero) }
    Box(
        modifier = Modifier
            .border(width = 1.dp, color = Color.Blue)
            .onGloballyPositioned { coordinates ->
                size = coordinates.size
            },
    ) {
        content()

        Text(
            text = "$size",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .background(Color.Yellow),
            fontSize = 10.sp
        )
    }
}

@Preview
@Composable
private fun DebugBoxPreview() {
    MaterialTheme {
        Surface {
            DebugBox {
                Button(
                    onClick = {},
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text("It's a button", Modifier.padding(8.dp))
                }
            }
        }
    }
}
