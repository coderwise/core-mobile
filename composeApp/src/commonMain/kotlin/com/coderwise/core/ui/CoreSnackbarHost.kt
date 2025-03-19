package com.coderwise.core.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CoreSnackbarHost(
    snackbarHostState: SnackbarHostState
) {
    SnackbarHost(
        hostState = snackbarHostState
    ) { snackbarData: SnackbarData ->

        val buttonColor = ButtonDefaults.textButtonColors(
            contentColor = MaterialTheme.colorScheme.inversePrimary
        )
        Snackbar(
            modifier = Modifier.padding(8.dp),
            action = {
                TextButton(
                    onClick = {
                        snackbarData.performAction()
                    },
                    colors = buttonColor
                ) {
                    Text(snackbarData.visuals.actionLabel ?: "Dismiss")
                }
            }
        ) {
            Text(snackbarData.visuals.message)
        }
    }
}