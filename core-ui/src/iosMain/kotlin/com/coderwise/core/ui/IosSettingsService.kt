package com.coderwise.core.ui

import platform.Foundation.NSURL
import platform.UIKit.UIApplication
import platform.UIKit.UIApplicationOpenSettingsURLString

class IosSettingsService : OsSettingsService {

    override fun openSettings() {
        val settingsUrl = NSURL.URLWithString(UIApplicationOpenSettingsURLString)
        if (settingsUrl != null && UIApplication.sharedApplication.canOpenURL(settingsUrl)) {
            UIApplication.sharedApplication.openURL(settingsUrl, emptyMap<Any?, Any?>()) {}
        }
    }
}