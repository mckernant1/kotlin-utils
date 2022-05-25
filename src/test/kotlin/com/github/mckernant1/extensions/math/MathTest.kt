package com.github.mckernant1.extensions.math

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class MathTest {

    @Test
    fun intPow() {
        val i: Int = 2
        assertEquals(4, i.pow(2))
    }

    @Test
    fun longPow() {
        val i: Long = 2
        assertEquals(4, i.pow(2))
    }

    @Test
    fun isInt() {
        assertTrue((2.0).isInt())
        assertTrue((0.0).isInt())
        assertFalse((2.2).isInt())
    }

    @Test
    fun gcf() {
        assertEquals((4).greatestCommonFactor(10), 2)
        assertEquals((1).greatestCommonFactor(10), 1)
        assertEquals((20).greatestCommonFactor(15), 5)
        assertEquals((42).greatestCommonFactor(56), 14)
    }

    @Test
    fun lcm() {
        assertEquals((10).leastCommonMultiple(25), 50)
        assertEquals((7).leastCommonMultiple(3), 21)
    }
}
