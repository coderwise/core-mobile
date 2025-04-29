package com.coderwise.core.ui.component

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier


@Composable
fun <Item> CoreOutlinedDropdown(
    options: List<Item>,
    onOptionSelected: (Item) -> Unit,
    modifier: Modifier = Modifier,
    selectedOption: Item? = null,
    label: String? = null,
    itemLabel: (Item) -> String = { it.toString() }
) {
    CoreBaseDropdown(
        options = options,
        onOptionSelected = onOptionSelected,
        modifier = modifier,
        selectedOption = selectedOption,
        label = label,
        itemLabel = itemLabel,
        outlined = true
    )
}

@Composable
fun <Item> CoreDropdown(
    options: List<Item>,
    onOptionSelected: (Item) -> Unit,
    modifier: Modifier = Modifier,
    selectedOption: Item? = null,
    label: String,
    itemLabel: (Item) -> String = { it.toString() }
) {
    CoreBaseDropdown(
        options = options,
        onOptionSelected = onOptionSelected,
        modifier = modifier,
        selectedOption = selectedOption,
        label = label,
        itemLabel = itemLabel
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun <Item> CoreBaseDropdown(
    options: List<Item>,
    onOptionSelected: (Item) -> Unit,
    modifier: Modifier = Modifier,
    selectedOption: Item? = null,
    label: String? = null,
    itemLabel: (Item) -> String = { it.toString() },
    outlined: Boolean = false
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember(selectedOption) {
        mutableIntStateOf(
            if (selectedOption != null)
                options.indexOfOrZero(selectedOption)
            else
                0
        )
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {
        if (outlined) {
            OutlinedTextField(
                modifier = modifier.menuAnchor(type = MenuAnchorType.PrimaryNotEditable),
                readOnly = true,
                value = itemLabel(options[selectedIndex]),
                onValueChange = {},
                label = { label?.let { Text(label) } },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = ExposedDropdownMenuDefaults.textFieldColors()
            )
        } else {
            TextField(
                modifier = modifier.menuAnchor(type = MenuAnchorType.PrimaryNotEditable),
                readOnly = true,
                value = itemLabel(options[selectedIndex]),
                onValueChange = {},
                label = { label?.let { Text(label) } },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = ExposedDropdownMenuDefaults.textFieldColors()
            )
        }

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEachIndexed { index, option ->
                DropdownMenuItem(
                    text = { Text(itemLabel(option)) },
                    onClick = {
                        expanded = false
                        selectedIndex = index
                        onOptionSelected(option)
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}

private fun <T> List<T>.indexOfOrZero(match: T): Int {
    val index = indexOf(match)
    return if (index == -1) 0 else index
}
