package com.coderwise.core.domain.arch

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

sealed interface Outcome<out T> {
    data class Success<out T>(val data: T) : Outcome<T>
    data class Error(val exception: Throwable) : Outcome<Nothing> {
        override fun toString() = exception.message ?: exception.toString()
    }
}

suspend fun <T> Outcome<T>.onSuccess(
    block: suspend (T) -> Unit
): Outcome<T> = when (this) {
    is Outcome.Error -> this
    is Outcome.Success -> {
        block(this.data)
        this
    }
}

suspend fun <T> Outcome<T>.onError(
    block: suspend (Throwable) -> Unit
): Outcome<T> = when (this) {
    is Outcome.Error -> {
        block(exception)
        this
    }

    is Outcome.Success -> this
}

suspend fun <T, R> Outcome<T>.mapSuccess(block: suspend (T) -> Outcome<R>): Outcome<R> =
    when (this) {
        is Outcome.Success -> block(this.data)
        is Outcome.Error -> this
    }

suspend fun <T> Outcome<T>.mapError(block: suspend (Throwable) -> Outcome<T>): Outcome<T> =
    when (this) {
        is Outcome.Success -> this
        is Outcome.Error -> block(exception)
    }

fun <T> Outcome<T>.dataOrNull(): T? =
    when (this) {
        is Outcome.Success -> this.data
        is Outcome.Error -> null
    }

fun <T, R> Flow<Outcome<T>>.mapSuccess(transform: suspend (T) -> R): Flow<Outcome<R>> =
    map { outcome ->
        when (outcome) {
            is Outcome.Success -> Outcome.Success(transform(outcome.data))
            is Outcome.Error -> Outcome.Error(outcome.exception)
        }
    }

fun <T> Flow<Outcome<T>>.filterSuccess(): Flow<T> = filter {
    it is Outcome.Success
}.map {
    (it as Outcome.Success).data
}
