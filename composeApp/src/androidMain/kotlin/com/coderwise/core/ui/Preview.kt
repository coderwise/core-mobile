package com.coderwise.core.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coderwise.core.ui.component.CoreOutlinedDropdown
import com.coderwise.core.ui.location.LocationPreview
import com.coderwise.core.ui.permissions.PermissionsPreview
import com.coderwise.core.ui.theme.ScreenPreview

@Preview
@Composable
fun ComponentsPreview() {
    ScreenPreview {
        Column(Modifier.padding(16.dp)) {
            CoreOutlinedDropdown(
                onOptionSelected = {},
                options = listOf("option 1", "option 2"),
                selectedOption = "selectedOption",
                label = "label"
            )
        }
    }
}

@Preview
@Composable
fun PermissionsScreenPreview() {
    PermissionsPreview()
}

@Preview
@Composable
fun LocationScreenPreview() {
    LocationPreview()
}
