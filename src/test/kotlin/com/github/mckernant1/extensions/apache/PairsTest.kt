package com.github.mckernant1.extensions.apache

import com.github.mckernant1.extensions.apache.Pairs.expandNullable
import com.github.mckernant1.extensions.apache.Pairs.flattenNullable
import com.github.mckernant1.extensions.apache.Pairs.swap
import org.apache.commons.lang3.tuple.Pair
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PairsTest {

    @Test
    fun swap() {
        assertEquals(Pair.of(1, 2), Pair.of(2, 1).swap())
    }

    @Test
    fun flattenNullable() {

        assertEquals(Pair.of(1, 1), Pair.of<Int, Int>(1, 1).flattenNullable(0, 0))

        assertEquals(Pair.of(1, 0), Pair.of<Int, Int>(1, null).flattenNullable(0, 0))

        assertEquals(Pair.of(0, 1), Pair.of<Int, Int>(null, 1).flattenNullable(0, 0))

        assertEquals(Pair.of(0, 0), Pair.of<Int, Int>(null, null).flattenNullable(0, 0))
    }

    @Test
    fun expandNullable() {
        val p: Pair<Int, Int>? = null
        assertEquals(Pair.of(null, null), p.expandNullable())
        assertEquals(Pair.of(1, 2), Pair.of(1, 2).expandNullable())
    }
}
