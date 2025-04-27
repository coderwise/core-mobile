package com.coderwise.core.sample

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.coderwise.core.auth.domain.SessionRepository
import com.coderwise.core.auth.ui.authNavigationGraph
import com.coderwise.core.sample.ui.catalog.CatalogRoute
import com.coderwise.core.sample.ui.list.ListRoute
import com.coderwise.core.sample.ui.location.LocationRoute
import com.coderwise.core.sample.ui.permissions.PermissionsRoute
import com.coderwise.core.sample.ui.sampleNavigationGraph
import com.coderwise.core.sample.ui.theme.Core_LibraryTheme
import com.coderwise.core.ui.arch.SimpleViewModel
import com.coderwise.core.ui.arch.UiText
import com.coderwise.core.ui.arch.rememberNavRouter
import com.coderwise.core.ui.component.CoreScaffold
import com.coderwise.core.ui.component.NavItem
import com.coderwise.core.ui.component.scaffold
import core_library.sampleapp.composeapp.generated.resources.Res
import core_library.sampleapp.composeapp.generated.resources.home_24px
import core_library.sampleapp.composeapp.generated.resources.location_on_24px
import core_library.sampleapp.composeapp.generated.resources.outline_atr_24
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.subscribeOn
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App() {
    Core_LibraryTheme {
        RootUi()
    }
}

class AppViewModel(
    sessionRepository: SessionRepository
) : SimpleViewModel<Unit>(initialState = Unit) {
    init {
        asyncAction { sessionRepository.session.collect {
            it.toString()
        } }
    }
}

@Composable
private fun RootUi() {
    val navController = rememberNavController()
    rememberNavRouter(navController)

    koinViewModel<AppViewModel>()

    CoreScaffold(
        navController = navController
    ) { innerPadding ->
        scaffold {
            showTopBar = true
            showBackNavigation = false
            topBarTitle = "Home"
            showBottomBar = true
            bottomBarNavItems = listOf(
                NavItem(ListRoute, UiText.Plain("Home"), Res.drawable.home_24px),
                NavItem(PermissionsRoute, UiText.Plain("Permissions"), Res.drawable.home_24px),
                NavItem(LocationRoute, UiText.Plain("Location"), Res.drawable.location_on_24px),
                NavItem(
                    CatalogRoute,
                    UiText.Plain("Catalog"),
                    Res.drawable.outline_atr_24
                )
            )
        }
        NavHost(
            navController = navController,
            startDestination = ListRoute,
            modifier = Modifier.padding(innerPadding)
        ) {
            sampleNavigationGraph()
            authNavigationGraph()
        }
    }
}
