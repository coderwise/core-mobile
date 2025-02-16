package com.coderwise.core.data

import com.coderwise.core.domain.arch.Outcome
import kotlinx.coroutines.flow.Flow

data class Sample(val value: String)

interface SampleRepository {
    val flow: Flow<Outcome<Sample>>
}