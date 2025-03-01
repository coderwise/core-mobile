package com.coderwise.core.ui.sample

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.coderwise.core.ui.theme.Core_LibraryTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SampleScreen(
    viewModel: SampleViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    SampleContent(
        uiState = uiState,
        dispatch = viewModel::dispatch
    )
}

@Composable
private fun SampleContent(
    uiState: SampleUiState,
    dispatch: (SampleAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
//        SearchRow(
//            //modifier = Modifier.fillMaxWidth()
//        )
        ItemsList(
            uiState = uiState,
            dispatch = dispatch,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun ItemsList(
    uiState: SampleUiState,
    dispatch: (SampleAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = modifier
        ) {
            items(uiState.items.size) { index ->
                val item = uiState.items[index]
                Text(
                    text = item.value,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .clickable {
                            dispatch(SampleAction.ItemClicked(item.id))
                        }
                )
            }
        }
        FloatingActionButton(
            onClick = {
                dispatch(SampleAction.AddButtonClicked)
            },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd)
        ) {
            Icon(Icons.Filled.Add, "Add")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchRow(
    modifier: Modifier = Modifier
) {
    var text by rememberSaveable { mutableStateOf("") }
    var expanded by rememberSaveable { mutableStateOf(false) }
    SearchBar(
        modifier = modifier,
        inputField = {
            SearchBarDefaults.InputField(
                query = text,
                onQueryChange = { text = it },
                onSearch = { expanded = false },
                expanded = expanded,
                onExpandedChange = { expanded = it },
                placeholder = { Text("Hinted search text") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                trailingIcon = { Icon(Icons.Default.MoreVert, contentDescription = null) },
            )
        },
        expanded = expanded, onExpandedChange = { expanded = it },
    ) {
        Column(Modifier.verticalScroll(rememberScrollState())) {
            repeat(4) { idx ->
                val resultText = "Suggestion $idx"
                ListItem(
                    headlineContent = { Text(resultText) },
                    supportingContent = { Text("Additional info") },
                    leadingContent = {
                        Icon(Icons.Filled.Star, contentDescription = null)
                    },
                    colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                    modifier = Modifier
                        .clickable {
                            text = resultText
                            expanded = false
                        }
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp))
            }
        }
    }
}

@Preview
@Composable
private fun SamplePreview() {
    Core_LibraryTheme {
        Surface {
            SampleContent(
                uiState = SampleUiState(
                    items = List(5) {
                        SampleUiState.Item(
                            id = it.toString(),
                            value = "Item $it"
                        )
                    }
                ),
                dispatch = {}
            )
        }
    }
}