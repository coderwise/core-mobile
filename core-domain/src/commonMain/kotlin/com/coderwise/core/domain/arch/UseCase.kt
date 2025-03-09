package com.coderwise.core.domain.arch

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

abstract class CoroutineUseCase<in Params, out Result>(
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    suspend operator fun invoke(params: Params): Outcome<Result> {
        return try {
            withContext(dispatcher) {
                execute(params)
            }
        } catch (e: Exception) {
            Outcome.Error(e)
        }
    }

    protected abstract suspend fun execute(params: Params): Outcome<Result>
}

suspend operator fun <Result> CoroutineUseCase<Unit, Result>.invoke(): Outcome<Result> = this(Unit)

abstract class FlowUseCase<in Params, out Result>(
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    operator fun invoke(params: Params): Flow<Outcome<Result>> = execute(params)
        .catch { e -> emit(Outcome.Error(Exception(e))) }
        .flowOn(dispatcher)

    protected abstract fun execute(params: Params): Flow<Outcome<Result>>
}

operator fun <Result> FlowUseCase<Unit, Result>.invoke(): Flow<Outcome<Result>> = this(Unit)