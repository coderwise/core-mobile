package com.coderwise.core.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.vector.ImageVector
import com.coderwise.core.ui.location.LocationRoute
import com.coderwise.core.ui.permissions.PermissionsRoute
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

interface Route {
    val hasBackNavigation: Boolean
}

@Serializable
data object Sample : Route {
    @Transient
    override val hasBackNavigation: Boolean = false
}

@Serializable
data class Edit(val id: Int) : Route {
    @Transient
    override val hasBackNavigation: Boolean = true
}

enum class NavItems(
    val route: Any,
    val imageVector: ImageVector
) {
    SAMPLE(Sample, Icons.Default.Home),
    PERMISSIONS(PermissionsRoute, Icons.Default.CheckCircle),
    LOCATION(LocationRoute, Icons.Default.LocationOn);

    fun routeId() = route::class.qualifiedName!!
}