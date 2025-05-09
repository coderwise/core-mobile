package com.coderwise.core.sample.ui.catalog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.coderwise.core.sample.ui.theme.ScreenPreview
import com.coderwise.core.ui.component.CoreOutlinedDropdown
import com.coderwise.core.ui.component.CorePasswordField
import com.coderwise.core.ui.component.CoreProgressButton
import com.coderwise.core.ui.component.CorePropertyText
import com.coderwise.core.ui.component.CoreTextField
import com.coderwise.core.ui.component.CoreTopBar
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CatalogScreen(viewModel: CatalogViewModel = koinViewModel()) {

    CatalogContent()
}

@Composable
private fun CatalogContent() {
    LazyColumn(
        modifier = Modifier.padding(16.dp)
    ) {
        item {
            Text(text = "TopBar", modifier = Modifier.padding(vertical = 16.dp))
            CoreTopBar(
                title = "TopBar"
            )
            HorizontalDivider(Modifier.padding(vertical = 16.dp))
        }
        item {
            Text(text = "Buttons", modifier = Modifier.padding(vertical = 16.dp))
            FlowRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                CoreProgressButton(
                    text = "Button",
                    onClick = {}
                )
                CoreProgressButton(
                    text = "Disabled",
                    onClick = {},
                    enabled = false
                )
                CoreProgressButton(
                    text = "Progress",
                    onClick = {},
                    isProgress = true
                )
            }
            HorizontalDivider(Modifier.padding(vertical = 16.dp))
        }
        item {
            Text(text = "CoreTextField", modifier = Modifier.padding(vertical = 16.dp))
            FlowRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                CoreTextField(
                    value = "Core text field",
                    onValueChange = {}
                )
            }
            HorizontalDivider(Modifier.padding(vertical = 16.dp))
        }
        item {
            Text(text = "CorePasswordField", modifier = Modifier.padding(vertical = 16.dp))
            FlowRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                CorePasswordField(
                    value = "Core password field",
                    onValueChange = {}
                )
            }
            HorizontalDivider(Modifier.padding(vertical = 16.dp))
        }
        item {
            Text(text = "CorePropertyText", modifier = Modifier.padding(vertical = 16.dp))
            FlowRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                CorePropertyText(
                    text = "Core property text",
                    label = "Label"
                )
            }
            HorizontalDivider(Modifier.padding(vertical = 16.dp))
        }
        item {
            Text(text = "CoreDropDown", modifier = Modifier.padding(vertical = 16.dp))
            CoreOutlinedDropdown(
                options = listOf("Item 1", "Item 2", "Item 3"),
                onOptionSelected = {}
            )
            HorizontalDivider(Modifier.padding(vertical = 16.dp))
        }
    }
}

@Preview
@Composable
private fun CatalogContentPreview() {
    ScreenPreview { CatalogContent() }
}