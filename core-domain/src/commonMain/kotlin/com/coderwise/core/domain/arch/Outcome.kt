package com.coderwise.core.domain.arch

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

sealed interface Outcome<out T> {
    data class Success<out T>(val data: T) : Outcome<T>

    data class Error(val error: Throwable) : Outcome<Nothing> {
        val message: String get() = error.message ?: error.toString()

        override fun toString() = message
    }

    companion object
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
    block: suspend (Outcome.Error) -> Unit
): Outcome<T> = when (this) {
    is Outcome.Error -> {
        block(this)
        this
    }

    is Outcome.Success -> this
}

suspend fun <T, R> Outcome<T>.mapSuccess(block: suspend (T) -> R): Outcome<R> =
    when (this) {
        is Outcome.Success -> tryOutcome { block(this.data) }
        is Outcome.Error -> this
    }

suspend fun <T> Outcome<T>.mapError(block: suspend (Throwable) -> Outcome<T>): Outcome<T> =
    when (this) {
        is Outcome.Success -> this
        is Outcome.Error -> block(error)
    }

suspend fun <T1, T2> Outcome<T1>.then(next: suspend (T1) -> Outcome<T2>): Outcome<T2> =
    when (this) {
        is Outcome.Success -> next(this.data)
        is Outcome.Error -> this
    }

fun <T> Outcome<T>.dataOrNull(): T? = when (this) {
    is Outcome.Success -> this.data
    is Outcome.Error -> null
}

fun <T> Outcome<T>.dataOrThrow(): T = when (this) {
    is Outcome.Success -> this.data
    is Outcome.Error -> throw error
}

fun <T, R> Flow<Outcome<T>>.mapSuccess(transform: suspend (T) -> R): Flow<Outcome<R>> =
    map { outcome ->
        when (outcome) {
            is Outcome.Success -> Outcome.Success(transform(outcome.data))
            is Outcome.Error -> outcome
        }
    }

fun <T> Flow<Outcome<T>>.filterSuccess(): Flow<T> = filter {
    it is Outcome.Success
}.map {
    (it as Outcome.Success).data
}

suspend inline fun <T> tryOutcome(block: suspend () -> T): Outcome<T> = try {
    Outcome.Success(block())
} catch (e: Exception) {
    Outcome.Error(e)
}

fun Outcome.Companion.success() = Outcome.Success(Unit)

suspend fun <T1, T2, R> Outcome<T1>.combine(
    other: Outcome<T2>,
    transform: suspend (T1, T2) -> R
): Outcome<R> = tryOutcome {
    val data1 = this.dataOrThrow()
    val data2 = other.dataOrThrow()
    transform(data1, data2)
}

suspend fun <T1, T2, R> outcomeCombine(
    outcome1: Outcome<T1>,
    outcome2: Outcome<T2>,
    transform: suspend (a: T1, b: T2) -> R
): Outcome<R> = outcome1.combine(outcome2, transform)
