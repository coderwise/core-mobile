plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlinSerialization)
}

application {
    mainClass = "io.ktor.server.netty.EngineMain"
    val isDevelopment = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

dependencies {
    implementation(project(":core-auth:auth-server"))
    implementation(project(":sampleApp:server-api"))

    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)

    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)

    implementation(libs.ktor.server.call.logging.jvm)
    implementation(libs.logback.classic)

    implementation(libs.ktor.server.auth)

    implementation(libs.kotlinx.datetime)

    implementation(libs.koin.ktor)
    implementation(libs.koin.logger.slf4j)

    implementation(libs.mongodb.driver.core)
    implementation(libs.mongodb.driver.sync)
    implementation(libs.bson)
}
