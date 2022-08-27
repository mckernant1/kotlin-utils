package com.github.mckernant1.standalone

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class RangesTest {

    @Test
    fun testRanges() {
        assertTrue((1 ge_le 10).all { it >= 1 && it <= 10 })
        assertTrue((1 ge_lt 10).all { it >= 1 && it < 10 })
        assertTrue((1 gt_le 10).all { it > 1 && it <= 10 })
        assertTrue((1 gt_lt 10).all { it > 1 && it < 10 })

        assertFalse((1 ge_le 10).isEmpty())
        assertFalse((1 ge_lt 10).isEmpty())
        assertFalse((1 gt_le 10).isEmpty())
        assertFalse((1 gt_lt 10).isEmpty())

        assertTrue((10 le_ge 1).all { it <= 10 && it >= 1 })
        assertTrue((10 le_gt 1).all { it <= 10 && it > 1 })
        assertTrue((10 lt_ge 1).all { it < 10 && it >= 1 })
        assertTrue((10 lt_gt 1).all { it < 10 && it > 1 })

        assertFalse((10 le_ge 1).isEmpty())
        assertFalse((10 le_gt 1).isEmpty())
        assertFalse((10 lt_ge 1).isEmpty())
        assertFalse((10 lt_gt 1).isEmpty())

    }

}
