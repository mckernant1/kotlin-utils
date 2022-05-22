package com.github.mckernant1.extensions.math

import org.junit.jupiter.api.Assertions.assertEquals
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
}
