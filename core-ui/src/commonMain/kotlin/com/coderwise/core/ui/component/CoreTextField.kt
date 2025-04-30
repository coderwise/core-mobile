package com.coderwise.core.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.coderwise.core.ui.utils.CorePreview
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CoreTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    enabled: Boolean = true,
    singleLine: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        label = { label?.let { Text(it) } },
        singleLine = singleLine,
        keyboardOptions = keyboardOptions
    )
}

@Preview
@Composable
private fun CoreTextFieldPreview() {
    CorePreview {
        Column {
            CoreTextField(
                value = "Test value",
                onValueChange = {},
                modifier = Modifier
                    .padding(8.dp),
                label = "Label"
            )
        }
    }
}