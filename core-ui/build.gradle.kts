plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinMultiplatformLibrary)

    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)

    //alias(libs.plugins.mavenPublish)
    id("maven-publish")
}

kotlin {
    android {
        namespace = "com.coderwise.core.ui"
        compileSdk = libs.versions.compileSdk.get().toInt()
        minSdk = libs.versions.minSdk.get().toInt()
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.material.icons.extended)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.ui.tooling.preview)

            implementation(libs.navigation.compose)

            // koin
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
        }
    }
}

group = "com.coderwise.core"
version = libs.versions.coderwiseCore.get()

//mavenPublishing {
//    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
//
//    if (project.hasProperty("sign")) {
//        signAllPublications()
//    }
//
//    coordinates(group.toString(), "core-ui", version.toString())
//
//    pom {
//        name = "Core UI library"
//        description = "Core UI library"
//        inceptionYear = "2025"
//        url = "https://github.com/coderwise/core.mobile"
//        licenses {
//            license {
//                name = "The Apache License, Version 2.0"
//                url = "https://www.apache.org/licenses/LICENSE-2.0.txt"
//                distribution = "https://www.apache.org/licenses/LICENSE-2.0.txt"
//            }
//        }
//        developers {
//            developer {
//                id = "coderwise"
//                name = "Coderwise"
//                url = "https://github.com/coderwise"
//            }
//        }
//        scm {
//            url = "https://github.com/coderwise/core.mobile"
//            connection = "https://github.com/coderwise/core.mobile.git"
//            developerConnection = "git://github.com/coderwise/core.mobile.git"
//        }
//    }
//}