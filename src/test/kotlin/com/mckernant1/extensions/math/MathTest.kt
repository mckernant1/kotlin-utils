package com.mckernant1.extensions.math

import com.mckernant1.extensions.math.DoubleAlgebra.isInt
import com.mckernant1.extensions.math.DoubleAlgebra.pow
import com.mckernant1.extensions.math.IntAlgebra.greatestCommonFactor
import com.mckernant1.extensions.math.IntAlgebra.leastCommonMultiple
import com.mckernant1.extensions.math.IntAlgebra.pow
import com.mckernant1.extensions.math.LongAlgebra.pow
import com.mckernant1.extensions.math.Percentiles.percentile
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class MathTest {

    @Test
    fun intPow() {
        val i: Int = 2
        assertEquals(4.0, i.pow(2).toDouble())
    }

    @Test
    fun longPow() {
        val i: Long = 2
        assertEquals(4.0, i.pow(2).toDouble())
    }

    @Test
    fun doublePow() {
        val i: Double = 2.0
        assertEquals(4.0, i.pow(2).toDouble())
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

    @Test
    fun percentiles() {
        assertEquals(95.0, (1 until 100).percentile(95))
    }
}
