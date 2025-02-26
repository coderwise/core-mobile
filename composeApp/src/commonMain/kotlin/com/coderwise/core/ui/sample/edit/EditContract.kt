package com.coderwise.core.ui.sample.edit

import com.coderwise.core.data.Sample
import com.coderwise.core.ui.arch.Action


data class EditModelState(
    val sample: Sample? = null,
    val isProgress: Boolean = false
)

data class EditUiState(
    val sampleValue: String,
    val saveEnabled: Boolean,
    val valueEnabled: Boolean
)

interface EditAction : Action {
    object Save : EditAction

    data class ValueUpdated(val value: String) : EditAction
}