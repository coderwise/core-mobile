package com.coderwise.core.auth.data.remote

interface AuthTokenProvider {
    fun get(): String

    fun reset(token: String)
}