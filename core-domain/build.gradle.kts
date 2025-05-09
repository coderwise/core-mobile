plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinMultiplatformLibrary)

    id("maven-publish")
    id("signing")
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

group = "com.coderwise.core.domain"
version = libs.versions.coderwiseCore.get()

ext["signing.keyId"] = null
ext["signing.password"] = null
ext["signing.secretKeyRingFile"] = null

//publishing {
//    repositories {
//        maven {
//            //name = "central"
//            url = uri("https://central.sonatype.com/api/v1/")
//
//            credentials {
//                username = System.getenv("ORG_GRADLE_PROJECT_mavenCentralUsername")
//                password = System.getenv("ORG_GRADLE_PROJECT_mavenCentralPassword")
//            }
//        }
//    }
//
//    publications.withType<MavenPublication> {
//        groupId = group.toString()
//        artifactId = project.name
//        version = version.toString()
//
//        pom {
//            name = "Core Domain library"
//            description = "Core Domain library"
//            inceptionYear = "2025"
//            url = "https://github.com/coderwise/core.mobile"
//        }
//    }
//}
//
//signing {
//    sign(publishing.publications)
//}

//mavenPublishing {
//    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
//
//    if (project.hasProperty("sign")) {
//        signAllPublications()
//    }
//
//    coordinates(group.toString(), "core-domain", version.toString())
//
//    pom {
//        name = "Core Domain library"
//        description = "Core Domain library"
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