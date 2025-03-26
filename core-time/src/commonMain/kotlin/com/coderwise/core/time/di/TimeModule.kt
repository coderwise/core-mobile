package com.coderwise.core.time.di

import com.coderwise.core.time.TimeService
import com.coderwise.core.time.impl.TimeServiceImpl
import kotlinx.datetime.Clock
import org.koin.dsl.module

val coreTimeModule = module {
    single<TimeService> { TimeServiceImpl(Clock.System) }
}