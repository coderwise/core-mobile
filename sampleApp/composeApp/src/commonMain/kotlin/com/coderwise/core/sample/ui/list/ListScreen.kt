package com.coderwise.core.sample.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.coderwise.core.ui.component.TopBarAction
import com.coderwise.core.ui.component.scaffold
import com.coderwise.core.ui.utils.ScreenPreview
import core_library.sampleapp.composeapp.generated.resources.Res
import core_library.sampleapp.composeapp.generated.resources.outline_account_circle_24
import core_library.sampleapp.composeapp.generated.resources.outline_add_24
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ListScreen(
    viewModel: ListViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val accountIconPainter = painterResource(Res.drawable.outline_account_circle_24)
    scaffold {
        showTopBar = true
        showBackNavigation = false
        topBarTitle = "Home"
        topBarActions = listOf(
            TopBarAction(
                iconPainter = accountIconPainter
            ) { viewModel.dispatch(ListAction.OnAccountClicked) }
        )
        showBottomBar = true
    }

    ListContent(
        uiState = uiState,
        dispatch = viewModel::dispatch
    )
}

@Composable
private fun ListContent(
    uiState: ListUiState,
    dispatch: (ListAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        ItemsList(
            uiState = uiState,
            dispatch = dispatch,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
        )
    }
}

@Composable
private fun ItemsList(
    uiState: ListUiState,
    dispatch: (ListAction) -> Unit,
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
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable {
                            dispatch(ListAction.OnItemClicked(item.id))
                        }
                )
            }
        }
        FloatingActionButton(
            onClick = {
                dispatch(ListAction.OnAddButtonClicked)
            },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd)
        ) {
            Icon(painterResource(Res.drawable.outline_add_24), "Add")
        }
    }
}

@Preview
@Composable
private fun ListPreview() {
    ScreenPreview {
        ListContent(
            uiState = ListUiState(
                items = List(5) {
                    ListUiState.Item(
                        id = it,
                        value = "Item $it"
                    )
                }
            ),
            dispatch = {}
        )
    }
}
