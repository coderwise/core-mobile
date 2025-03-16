package com.coderwise.core.versioning

open class VersioningPluginExtension {

    private val lazyVersionCode by lazy { Versioning.getVersionCode() }
    private val lazyVersionName by lazy { Versioning.getVersionName() }

    fun getVersionCode() = lazyVersionCode

    fun getVersionName() = lazyVersionName
}