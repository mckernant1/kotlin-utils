package com.mckernant1.extensions.collections

import com.mckernant1.extensions.collections.Iterator.cycle
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class IteratorTest {

    @Test
    fun testCycle() {
        val l = listOf(1, 2, 3)
        assertEquals(9, l.cycle().take(9).toList().size)
        assertEquals(listOf(1, 2, 3, 1, 2, 3), l.cycle().take(6).toList())
    }

}
