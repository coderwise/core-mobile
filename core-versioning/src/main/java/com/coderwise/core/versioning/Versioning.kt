package com.coderwise.core.versioning

import com.coderwise.core.versioning.utils.runCommand
import org.gradle.api.GradleException

object Versioning {
    internal fun getVersionCode() = getCommitCount().also { println("VersionCode $it") }

    internal fun getVersionName() = getTag().also { println("VersionName $it") }

    private fun getCommitCount() = "git rev-list --count HEAD".runCommand().toIntOrNull()

    private fun getTag(): String {
        val revList = "git rev-list --tags --max-count=1".runCommand()
        val result = "git describe --tags $revList".runCommand()
        val isDirty = "git status --porcelain".runCommand().isNotEmpty()
        val compiledResult = if (isDirty) "$result-SNAPSHOT" else result
        return if (result.isNotEmpty()) compiledResult else throw GradleException("Error reading tag")
    }
}
