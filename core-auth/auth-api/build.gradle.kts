plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinMultiplatformLibrary)
    alias(libs.plugins.kotlinSerialization)

    id("maven-publish")
}

kotlin {
    androidLibrary {
        namespace = "com.coderwise.core.auth.api"
        compileSdk = libs.versions.compileSdk.get().toInt()
        minSdk = libs.versions.minSdk.get().toInt()
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    jvm()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.serialization.json)
        }
    }
}

publishing {
    repositories {
        mavenLocal()
    }
}

group = "com.coderwise.core"
version = libs.versions.coderwiseCore.get()
