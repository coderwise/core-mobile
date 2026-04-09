@file:Suppress("UnstableApiUsage")


enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

rootProject.name = "core-library"
include(
    ":sampleApp:composeApp",
    ":sampleApp:server",
    ":sampleApp:server-api"
)

include(":core-permissions")

include(":core-time")
include(":core-location")
include(":core-ui")
include(":core-domain")
include(":core-data")
include(":core-versioning")

include(":core-auth:auth-ui")
include(":core-auth:auth-domain")
include(":core-auth:auth-data")
include(":core-auth:auth-server")
include(":core-auth:auth-api")
