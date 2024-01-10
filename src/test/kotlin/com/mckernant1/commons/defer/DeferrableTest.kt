package com.mckernant1.commons.defer

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.opentest4j.AssertionFailedError

class DeferrableTest {

    @Test
    fun deferTest(): Unit {
        var a = 0
        fun testDeferFunc() = defer { d ->
            d.defer { a = 1 }
            assertTrue(false)
        }

        assertEquals(0, a)
        assertThrows<AssertionFailedError> {
            testDeferFunc()
        }
        assertEquals(1, a)
    }

}
