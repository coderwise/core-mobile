package com.coderwise.core.data.remote

interface UrlProvider {
    fun get(): String

    companion object {
        const val emulatorHost = "10.0.2.2"
    }
}