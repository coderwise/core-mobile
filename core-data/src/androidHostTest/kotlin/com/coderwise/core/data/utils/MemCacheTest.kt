package com.coderwise.core.data.utils

import com.coderwise.core.domain.repository.Identifiable
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class MemCacheTest {

    @Test
    fun `when set invoked with empty list, should emit new value`() = runTest {
        val memCache = ManyMemCache<String, TestIdentifiable>()

        val testOne = TestIdentifiable("TestOne")
        val testTwo = TestIdentifiable("TestTwo")
        memCache.set(listOf(testOne, testTwo))

        memCache.flow().take(1).collect {
            assertEquals(2, it.size)
        }

        memCache.set(emptyList())

        memCache.flow().take(1).collect {
            assertEquals(0, it.size)
        }
    }

    private class TestIdentifiable(override val id: String) : Identifiable<String> {}
}