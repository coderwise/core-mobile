package com.coderwise.core.auth.server

import com.coderwise.core.auth.server.google.FirebaseJWTVerifier
import com.google.firebase.auth.FirebaseAuth
import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.OAuthAccessTokenResponse
import io.ktor.server.auth.OAuthServerSettings
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.authentication
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.auth.oauth
import io.ktor.server.response.respond
import io.ktor.server.response.respondRedirect
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject

private val applicationHttpClient = HttpClient(Apache) {
    install(ContentNegotiation) {
        json()
    }
}

fun Application.configureGoogleOAuth() {
    val firebaseAuth by inject<FirebaseAuth>()
    val audience = "trips-mobile"
    val redirects = mutableMapOf<String, String>()
    val clientId = environment.config.property("firebase.clientId").getString()
    val clientSecret = environment.config.property("firebase.clientSecret").getString()


    install(Authentication) {
        jwt("auth-jwt") {
            verifier(FirebaseJWTVerifier(firebaseAuth, audience))
            validate { credential -> JWTPrincipal(credential.payload) }
            challenge { _, _ ->
                call.respond(HttpStatusCode.Unauthorized, "Token is not valid or has expired")
            }
        }
        oauth("auth-oauth-google") {
            urlProvider = { "http://localhost:8080/callback" }
            providerLookup = {
                OAuthServerSettings.OAuth2ServerSettings(
                    name = "google",
                    authorizeUrl = "https://accounts.google.com/o/oauth2/auth",
                    accessTokenUrl = "https://accounts.google.com/o/oauth2/token",
                    requestMethod = HttpMethod.Post,
                    clientId = clientId,//System.getenv("GOOGLE_CLIENT_ID"),
                    clientSecret = clientSecret,//System.getenv("GOOGLE_CLIENT_SECRET"),
                    defaultScopes = listOf("https://www.googleapis.com/auth/userinfo.profile"),
//                    onStateCreated = { call, state ->
//                        call.request.queryParameters["redirectUrl"]?.let {
//                            redirects[state] = it
//                        }
//                    }
                )
            }
            client = applicationHttpClient
        }
    }

    routing {
        authenticate("auth-oauth-google") {
            get("/login") {
                //call.respondRedirect("/callback")
            }
            get("/callback") {
                val contentPrincipal: OAuthAccessTokenResponse.OAuth2? =
                    call.authentication.principal()
                contentPrincipal?.let { principal ->
                    principal.state?.let { state ->
                        //call.sessions.set(UserSession(state, principal.accessToken))
                        redirects[state]?.let { redirect ->
                            call.respondRedirect(redirect)
                            return@get
                        }
                    }
                }
                call.respondRedirect("/home")
            }
        }
    }
}