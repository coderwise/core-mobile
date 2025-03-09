package com.coderwise.core.ui

import android.content.Context
import android.provider.Settings
import androidx.core.net.toUri
import com.coderwise.core.ui.utils.openPage

class AndroidSettingsService(
    private val context: Context
) : OsSettingsService {

    override fun openSettings() {
        context.openPage(
            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            uri = "package:${context.packageName}".toUri()
        )
    }
}