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
        compileSdk = 35
        minSdk = 24
    }

    listOf(iosX64(), iosArm64(), iosSimulatorArm64()).forEach {
        it.binaries.framework {
            baseName = "core-uiKit"
            isStatic = true
        }
    }
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
version = "1.0"
