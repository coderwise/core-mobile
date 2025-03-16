package com.coderwise.core.ui.sample

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.coderwise.core.ui.utils.CorePreview
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

@Preview
@Composable
private fun SamplePreview() {
    CorePreview {
        SampleContent(
            uiState = SampleUiState(
                items = List(5) {
                    SampleUiState.Item(
                        id = it,
                        value = "Item $it"
                    )
                }
            ),
            dispatch = {}
        )
    }
}
