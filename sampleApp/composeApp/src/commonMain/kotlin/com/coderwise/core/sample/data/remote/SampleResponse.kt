package com.coderwise.core.sample.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class SampleResponse(
    val id: Int,
    val value: String
)
