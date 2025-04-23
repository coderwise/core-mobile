package com.coderwise.core.auth.server.google

import com.google.firebase.auth.FirebaseAuth
import org.koin.dsl.module

val authModule = module {
    single {
        FirebaseAdmin.init()
        FirebaseAuth.getInstance()
    }
}