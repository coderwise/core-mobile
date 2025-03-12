package com.coderwise.core.data.arch

import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class MemoryLocalSourceTest {

    @Test
    fun getFlow() = runTest {
        val memLocalSource = MemoryLocalSource<String, Int>(
            identify = { it.hashCode() }
        )

        var count = 0

        memLocalSource.reset(listOf("1"))
        memLocalSource.flow.take(1).collect {
            count++
        }
        assertEquals(1, count)
    }
}