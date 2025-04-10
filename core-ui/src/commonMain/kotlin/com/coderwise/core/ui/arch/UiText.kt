package com.coderwise.core.ui.arch

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

sealed interface UiText {

    data class Plain(val text: String) : UiText

    class Resource(
        val id: StringResource,
        val args: Array<Any> = emptyArray()
    ) : UiText

    @Composable
    fun asString() = when (this) {
        is Plain -> text
        is Resource -> stringResource(resource = id, formatArgs = args)
    }
}
