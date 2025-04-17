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

rootProject.name = "Core_Library"
include(":sampleApp:composeApp")
include(":sampleApp:server")

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
