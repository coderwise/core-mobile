package com.coderwise.core.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.coderwise.core.ui.utils.CorePreview
import org.jetbrains.compose.ui.tooling.preview.Preview

data class TopBarConfiguration(
    val actions: List<@Composable () -> Unit> = emptyList()
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoreTopBar(
    title: String,
    modifier: Modifier = Modifier,
    showBackNavigation: Boolean = false,
    onNavigationClick: () -> Unit = {},
    actions: List<@Composable () -> Unit> = emptyList()
) {
    TopAppBar(
        title = { Text(text = title) },
        modifier = modifier,
        navigationIcon = {
            if (showBackNavigation) {
                IconButton(
                    onClick = { onNavigationClick() }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Localized description"
                    )
                }
            }
        },
        actions = {
            actions.forEach { it() }
        }
    )
}

@Preview
@Composable
internal fun CoreTopBarPreview() {
    CorePreview {
        Column {
            CoreTopBar(
                title = "Trips",
                showBackNavigation = true,
                onNavigationClick = {}
            )

            CoreTopBar(
                title = "Trips",
                showBackNavigation = false,
                onNavigationClick = {}
            )

            CoreTopBar(
                title = "Trips",
                showBackNavigation = true,
                onNavigationClick = {},
                actions = listOf {
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Localized description"
                        )
                    }
                }
            )
        }
    }
}