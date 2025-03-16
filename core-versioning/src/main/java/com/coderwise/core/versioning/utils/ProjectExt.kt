package com.coderwise.core.versioning.utils

import com.android.build.gradle.AppExtension
import org.gradle.api.Project
import org.gradle.api.plugins.BasePluginConvention

fun Project.android(): AppExtension {
    val android = extensions.getByType(AppExtension::class.java)
    if (android is AppExtension) {
        return android
    } else {
        throw IllegalStateException("Project $name is not an Android project")
    }
}

fun Project.archivesBaseName(): String {
    return convention.findPlugin(BasePluginConvention::class.java)?.archivesBaseName ?: name
}