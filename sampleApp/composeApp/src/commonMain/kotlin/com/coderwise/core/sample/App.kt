package com.coderwise.core.sample

import androidx.compose.runtime.Composable
import com.coderwise.core.auth.ui.authNavigationGraph
import com.coderwise.core.sample.ui.AppViewModel
import com.coderwise.core.sample.ui.catalog.CatalogRoute
import com.coderwise.core.sample.ui.list.ListRoute
import com.coderwise.core.sample.ui.location.LocationRoute
import com.coderwise.core.sample.ui.permissions.PermissionsRoute
import com.coderwise.core.sample.ui.sampleNavigationGraph
import com.coderwise.core.sample.ui.theme.Core_LibraryTheme
import com.coderwise.core.ui.arch.UiText
import com.coderwise.core.ui.component.CoreScaffold
import com.coderwise.core.ui.component.NavItem
import core_library.sampleapp.composeapp.generated.resources.Res
import core_library.sampleapp.composeapp.generated.resources.home_24px
import core_library.sampleapp.composeapp.generated.resources.location_on_24px
import core_library.sampleapp.composeapp.generated.resources.lock_open_24px
import core_library.sampleapp.composeapp.generated.resources.outline_atr_24
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App() {
    Core_LibraryTheme {
        RootUi()
    }
}

val rootBottomBarNavItems = listOf(
    NavItem(ListRoute, UiText.Plain("Home"), Res.drawable.home_24px),
    NavItem(PermissionsRoute, UiText.Plain("Permissions"), Res.drawable.lock_open_24px),
    NavItem(LocationRoute, UiText.Plain("Location"), Res.drawable.location_on_24px),
    NavItem(
        CatalogRoute,
        UiText.Plain("Catalog"),
        Res.drawable.outline_atr_24
    )
)

@Composable
private fun RootUi() {
    koinViewModel<AppViewModel>()

    CoreScaffold(
        scaffoldStateBuilder = {
            showTopBar = true
            showBackNavigation = false
            topBarTitle = "Home"
            showBottomBar = true
            bottomBarNavItems = rootBottomBarNavItems
        },
        startDestination = ListRoute,
    ) {
        sampleNavigationGraph()
        authNavigationGraph()
    }
}
