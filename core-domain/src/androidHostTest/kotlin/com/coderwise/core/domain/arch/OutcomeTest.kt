package com.coderwise.core.domain.arch

import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class OutcomeTest {

    @Test
    fun `when tryOutcome block throws exception, should return Error`() = runTest {
        val actual = tryOutcome {
            throw RuntimeException("test")
        }
        assertTrue(actual is Outcome.Error)
    }

    @Test
    fun `when two Success are combined, should invoke transform block`() = runTest {
        val outcome1 = Outcome.Success("test1")
        val outcome2 = Outcome.Success("test2")
        val actual = outcome1.combine(outcome2) { d1, d2 ->
            d1 + d2
        }
        val expected = Outcome.Success("test1test2")

        assertEquals(expected, actual)
    }

    @Test
    fun `when second outcome is Error, should return Error with wrapped exception`() = runTest {
        val testError = RuntimeException("test")
        val outcome1 = Outcome.Success("test1")
        val outcome2 = tryOutcome<String> { throw testError }

        val actual = outcomeCombine(outcome1, outcome2) { d1, d2 ->
            "$d1$d2"
        }
        val expected = Outcome.Error(testError)

        assertEquals(expected, actual)
    }
}