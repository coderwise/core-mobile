package com.coderwise.core.auth.server.google

import com.auth0.jwt.interfaces.Claim
import com.auth0.jwt.interfaces.DecodedJWT
import com.auth0.jwt.interfaces.JWTVerifier
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseToken
import java.util.Date

class FirebaseJWTVerifier(
    private val firebaseAuth: FirebaseAuth,
    private val audience: String
) : JWTVerifier {

    override fun verify(token: String): DecodedJWT {
        val parts = token.split(".")
        val header = parts[0]
        val payload = parts[1]

        val firebaseToken = firebaseAuth.verifyIdToken(token)
        return FirebaseDecodedToken(firebaseToken, audience, token, header, payload)
    }

    override fun verify(jwt: DecodedJWT?): DecodedJWT? {
        return null
    }
}

class FirebaseDecodedToken(
    private val firebaseToken: FirebaseToken,
    private val audience: String,
    private val idToken: String,
    private val header: String,
    private val payload: String
) : DecodedJWT {
    override fun getIssuer(): String {
        return firebaseToken.issuer
    }

    override fun getSubject(): String {
        return ""
    }

    override fun getAudience(): MutableList<String> {
        return listOf(audience).toMutableList()
    }

    override fun getExpiresAt(): Date {
        return Date()
    }

    override fun getNotBefore(): Date {
        return Date()
    }

    override fun getIssuedAt(): Date {
        return Date()
    }

    override fun getId(): String {
        return firebaseToken.uid
    }

    override fun getClaim(name: String?): Claim? {
        return null
    }

    override fun getClaims(): MutableMap<String, Claim> {
        return mutableMapOf()
    }

    override fun getAlgorithm(): String {
        return ""
    }

    override fun getType(): String {
        return ""
    }

    override fun getContentType(): String {
        return ""
    }

    override fun getKeyId(): String {
        return ""
    }

    override fun getHeaderClaim(name: String?): Claim? {
        return null
    }

    override fun getToken(): String {
        return idToken
    }

    override fun getHeader(): String {
        return header
    }

    override fun getPayload(): String {
        return payload
    }

    override fun getSignature(): String {
        return ""
    }
}