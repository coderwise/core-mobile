package com.coderwise.core.versioning.utils

import com.android.build.gradle.api.ApplicationVariant
import org.gradle.api.Task
import java.io.File

internal fun ApplicationVariant.generateOutputName(
    baseName: String,
    extension: String
) = buildString {
    append(baseName)
    productFlavors.forEach {
        append("-")
        append(it.name)
    }
    append("-")
    append(versionName)
    append("-")
    append(versionCode.toString())
    append("-")
    append(buildType.name)
    if (extension == "apk" && !isSigningReady) {
        append("-unsigned")
    } else if (extension == "txt") {
        append("-mapping")
    }
    append(".")
    append(extension)
}

internal fun ApplicationVariant.getBundlePath(buildDir: File) = buildString {
    append(buildDir.absolutePath)
    append("/outputs/bundle/")
    append(name)
    append("/")
}

internal fun ApplicationVariant.getAPKPath(buildDir: File) = buildString {
    append(buildDir.absolutePath)
    append("/outputs/apk/")
    if (flavorName.isNotEmpty()) {
        append(flavorName)
        append("/")
    }
    append(buildType.name)
    append("/")
}

internal fun ApplicationVariant.addRenameMappingAction(task: Task) {
    if (this.buildType.isMinifyEnabled) {
        this.mappingFileProvider.orNull?.firstOrNull()?.let {
            val newMappingName =
                this.generateOutputName(baseName, "txt")
            task.addRenameMappingAction(it, newMappingName)
        }
    }
}