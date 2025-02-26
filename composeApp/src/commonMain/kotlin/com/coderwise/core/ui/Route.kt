package com.coderwise.core.ui

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
data class Edit(val id: String) : Route {
    @Transient
    override val hasBackNavigation: Boolean = true
}
