package com.coderwise.core.auth.server

import java.security.MessageDigest
import java.security.SecureRandom

class HashingService {

    @OptIn(ExperimentalStdlibApi::class)
    fun generateSaltedHash(value: String, saltLength: Int = 32): SaltedHash {
        val salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(saltLength)
        val saltAsHex = salt.toHexString()
        val hash = (saltAsHex + value).sha256()
        return SaltedHash(
            hash = hash,
            salt = saltAsHex
        )
    }

    fun verify(value: String, saltedHash: SaltedHash): Boolean {
        return (saltedHash.salt + value).sha256() == saltedHash.hash
    }
}

@OptIn(ExperimentalStdlibApi::class)
fun String.sha256(): String {
    return MessageDigest
        .getInstance("SHA-256")
        .digest(this.toByteArray())
        .toHexString()
}