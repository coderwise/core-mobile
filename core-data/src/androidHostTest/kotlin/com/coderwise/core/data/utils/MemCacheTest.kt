package com.coderwise.core.data.utils

import kotlinx.coroutines.flow.take
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class MemCacheTest {

    @Test
    fun `when set invoked with empty list, should emit new value`() = runTest {
        val memCache = ManyMemCache<String, Int>(
            identify = { it.hashCode() }
        )

        val testOne = "TestOne"
        val testTwo = "TestTwo"
        memCache.set(listOf(testOne, testTwo))

        memCache.flow().take(1).collect {
            assertEquals(2, it.size)
        }

        memCache.set(emptyList())

        memCache.flow().take(1).collect {
            assertEquals(0, it.size)
        }
    }
}