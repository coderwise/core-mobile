package com.coderwise.core.sample.server.api

import kotlinx.serialization.Serializable

@Serializable
data class SampleDto(
    val id: Int,
    val value: String
)
