package com.coderwise.core.ui.arch

interface UiMessenger {
    suspend fun showMessage(message: String)
}