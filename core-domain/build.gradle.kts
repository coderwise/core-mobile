plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinMultiplatformLibrary)

    id("maven-publish")
}

kotlin {
    androidLibrary {
        namespace = "com.coderwise.core.domain"
        compileSdk = libs.versions.compileSdk.get().toInt()
        minSdk = libs.versions.minSdk.get().toInt()

        @Suppress("UnstableApiUsage")
        withHostTestBuilder {
        }
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    jvm()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
        }
    }
}

publishing {
    repositories {
        mavenLocal()
    }
}

group = "com.coderwise.core.domain"
version = libs.versions.coderwiseCore.get()
