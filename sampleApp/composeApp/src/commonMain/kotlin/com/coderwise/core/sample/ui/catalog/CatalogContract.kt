package com.coderwise.core.sample.ui.catalog

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object CatalogRoute

fun NavGraphBuilder.catalogScreen() {
    composable<CatalogRoute> { CatalogScreen() }
}