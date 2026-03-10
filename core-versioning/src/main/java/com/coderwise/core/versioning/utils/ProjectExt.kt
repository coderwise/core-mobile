package com.coderwise.core.versioning.utils

import com.android.build.gradle.AppExtension
import org.gradle.api.Project
import org.gradle.api.plugins.BasePluginExtension

fun Project.android(): AppExtension {
    return extensions.getByType(AppExtension::class.java)
}

fun Project.archivesBaseName(): String {
    return extensions.findByType(BasePluginExtension::class.java)?.archivesName?.get() ?: name
}
