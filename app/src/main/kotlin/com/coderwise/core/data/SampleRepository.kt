package com.coderwise.core.data

import kotlinx.coroutines.flow.Flow

data class Sample(val value: String)

interface SampleRepository {
    val flow: Flow<Sample>
}