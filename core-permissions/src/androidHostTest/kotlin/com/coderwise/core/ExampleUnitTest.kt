package com.coderwise.core

import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

interface Example {
    fun example(): String
}

class ExampleUnitTest {
    private val mockExample = mockk<Example>()

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun `test mockk library`() = runTest {
        every { mockExample.example() } returns "example"

        assertEquals("example", mockExample.example())
    }
}