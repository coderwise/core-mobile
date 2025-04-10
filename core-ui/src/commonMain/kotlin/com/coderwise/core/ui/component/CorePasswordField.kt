package com.coderwise.core.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.coderwise.core.ui.utils.ScreenPreview
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CorePasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    enabled: Boolean = true,
    showPassword: Boolean = false
) {
    var showPassword by remember { mutableStateOf(showPassword) }

    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange(it) },
        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(
            '\u25CF'
        ),
        //textObfuscationMode = if (showPassword) TextObfuscationMode.Visible else TextObfuscationMode.RevealLastTyped,
        //textObfuscationCharacter = '\u25CF',
        modifier = modifier,
        label = { label?.let { Text(label) } },
        enabled = enabled,
        trailingIcon = {
            IconButton(
                onClick = { showPassword = !showPassword }
            ) {
                Icon(
                    imageVector = if (showPassword) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                    contentDescription = if (showPassword) "Hide password" else "Show password"
                )
            }
        }
    )
}

@Preview
@Composable
private fun CorePasswordFieldPreview() {
    ScreenPreview {
        Column {
            CorePasswordField(
                value = "testPassword",
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                label = "Password"
            )
            CorePasswordField(
                value = "testPassword",
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                showPassword = true
            )
        }
    }
}