package com.coderwise.core.time.di

import com.coderwise.core.time.TimeService
import com.coderwise.core.time.impl.TimeServiceImpl
import org.koin.dsl.module
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
val coreTimeModule = module {
    single<TimeService> { TimeServiceImpl(Clock.System) }
}