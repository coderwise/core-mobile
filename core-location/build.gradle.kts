plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinMultiplatformLibrary)
    id("maven-publish")
}

kotlin {
    androidLibrary {
        namespace = "com.coderwise.core.location"
        compileSdk = libs.versions.compileSdk.get().toInt()
        minSdk = libs.versions.minSdk.get().toInt()
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    jvm()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)

            // kotlinx DateTime
            implementation(libs.kotlinx.datetime)

            // koin
            implementation(libs.koin.core)
        }

        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
            // DI
            implementation(libs.koin.android)
        }
    }
}

group = "com.coderwise.core.location"
version = libs.versions.coderwiseCore.get()