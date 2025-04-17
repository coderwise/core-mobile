plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlinSerialization)
}

dependencies {
    implementation(project(":core-auth:auth-data"))

    implementation(libs.ktor.server.core)

    implementation(libs.ktor.server.auth)
    implementation(libs.ktor.server.auth.jwt)
}