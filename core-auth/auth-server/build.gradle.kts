plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlinSerialization)

    id("java-library")
    id("maven-publish")
}

dependencies {
    implementation(project(":core-auth:auth-api"))

    implementation(libs.ktor.server.core)

    implementation(libs.ktor.server.auth)
    implementation(libs.ktor.server.auth.jwt)

    implementation(libs.ktor.client.apache)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)

    // koin
    implementation(libs.koin.core)
    implementation(libs.koin.ktor)

    // Firebase
    implementation(libs.firebase.admin)
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = project.name
            from(components["kotlin"])
        }
    }
    repositories {
        mavenLocal()
    }
}

group = "com.coderwise.core.auth"
version = libs.versions.coderwiseCore.get()
