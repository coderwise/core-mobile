package com.coderwise.core.sample.ui

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.coderwise.core.ui.component.scaffold
import com.coderwise.core.sample.ui.list.ListScreen
import com.coderwise.core.sample.ui.list.edit.EditScreen
import com.coderwise.core.sample.ui.location.LocationScreen
import com.coderwise.core.sample.ui.permissions.PermissionsScreen
import kotlinx.serialization.Serializable

@Serializable
data object ListRoute

@Serializable
data class EditRoute(val id: Int)

@Serializable
data object PermissionsRoute

@Serializable
data object LocationRoute

fun NavGraphBuilder.sampleNavigationGraph() {
    composable<ListRoute> {
        ListScreen()
    }
    composable<PermissionsRoute> {
        PermissionsScreen()
    }
    composable<LocationRoute> {
        LocationScreen()
    }
    composable<EditRoute> {
        scaffold {
            showBackNavigation = true
            topBarTitle = "Edit"
            topBarActions = emptyList()
            showBottomBar = false
        }

        EditScreen()
    }
}
