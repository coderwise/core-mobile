plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinMultiplatformLibrary)

    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)

    id("maven-publish")
}

kotlin {
    androidLibrary {
        namespace = "com.coderwise.core.ui"
        compileSdk = libs.versions.compileSdk.get().toInt()
        minSdk = libs.versions.minSdk.get().toInt()
    }

    listOf(iosX64(), iosArm64(), iosSimulatorArm64()).forEach {
        it.binaries.framework {
            baseName = "core-uiKit"
            isStatic = true
        }
    }

    jvm("desktop")

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            implementation(libs.navigation.compose)

            // koin
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
        }

        androidMain.dependencies {
            implementation(libs.compose.ui.tooling)
        }

        iosMain.dependencies {
        }
    }
}

publishing {
    repositories {
        mavenLocal()
    }
}

group = "com.coderwise.core.ui"
version = libs.versions.coderwiseCore.get()
