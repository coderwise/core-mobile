package com.coderwise.core.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.coderwise.core.ui.utils.CorePreview
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CoreCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            enabled = enabled
        )
        Text(
            text = label,
            modifier = Modifier.clickable {
                if (enabled) onCheckedChange(!checked)
            }
        )
    }
}

@Preview
@Composable
private fun CoreCheckboxPreview() {
    CorePreview {
        CoreCheckbox(
            checked = true,
            onCheckedChange = {},
            label = "Remember me",
            modifier = Modifier.padding(8.dp)
        )
    }
}
