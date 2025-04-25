package com.coderwise.core.sample.ui

import androidx.navigation.NavGraphBuilder
import com.coderwise.core.sample.ui.catalog.catalogScreen
import com.coderwise.core.sample.ui.list.edit.editScreen
import com.coderwise.core.sample.ui.list.listScreen
import com.coderwise.core.sample.ui.location.locationScreen
import com.coderwise.core.sample.ui.permissions.permissionsScreen
import com.coderwise.core.sample.ui.profile.profileScreen


fun NavGraphBuilder.sampleNavigationGraph() {
    listScreen()
    permissionsScreen()
    locationScreen()
    editScreen()
    profileScreen()
    catalogScreen()
}
