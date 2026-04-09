package com.coderwise.core.sample.ui.catalog

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.coderwise.core.ui.component.scaffold
import kotlinx.serialization.Serializable

@Serializable
data object CatalogRoute

fun NavGraphBuilder.catalogScreen() {
    composable<CatalogRoute> {
        scaffold {
            showBackNavigation = false
            topBarTitle = "Catalog"
            showBottomBar = true
            topBarActions = listOf()
        }
        CatalogScreen()
    }
}