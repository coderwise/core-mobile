package com.coderwise.core.auth.server

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.util.Date
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class JwtService {
    @OptIn(ExperimentalTime::class)
    fun create(
        tokenConfig: JwtConfig,
        tokenClaim: TokenClaim
    ): String {
        return JWT.create()
            .withAudience(tokenConfig.audience)
            .withIssuer(tokenConfig.issuer)
            .withClaim(tokenClaim.name, tokenClaim.value)
            .withExpiresAt(
                Date(
                    Clock.System.now().plus(tokenConfig.expiration).toEpochMilliseconds()
                )
            )
            .sign(Algorithm.HMAC256(tokenConfig.secret))
    }
}