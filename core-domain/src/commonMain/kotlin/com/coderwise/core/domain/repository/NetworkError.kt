package com.coderwise.core.domain.repository

data class NetworkError(override val message: String) : Exception()