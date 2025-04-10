plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinMultiplatformLibrary)

    alias(libs.plugins.kotlinSerialization)

    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeMultiplatform)

    id("maven-publish")
}

kotlin {
    androidLibrary {
        namespace = "com.coderwise.core.auth.ui"
        compileSdk = libs.versions.compileSdk.get().toInt()
        minSdk = libs.versions.minSdk.get().toInt()
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(project(":core-ui"))
            implementation(project(":core-domain"))
            implementation(project(":core-auth:auth-domain"))

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.materialIconsExtended)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            // koin
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)

            implementation(libs.navigation.compose)

            implementation(libs.kotlinx.serialization.protobuf)
        }
    }
}

publishing {
    repositories {
        mavenLocal()
    }
}

group = "com.coderwise.core.auth"
version = libs.versions.coderwiseCore.get()
