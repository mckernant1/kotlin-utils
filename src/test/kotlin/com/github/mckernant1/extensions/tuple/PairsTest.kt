package com.github.mckernant1.extensions.tuple

import com.github.mckernant1.extensions.tuple.Pairs.expandNullable
import com.github.mckernant1.extensions.tuple.Pairs.flattenNullable
import com.github.mckernant1.extensions.tuple.Pairs.swap
import kotlin.Pair
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PairsTest {

    @Test
    fun swap() {
        assertEquals(Pair(1, 2), Pair(2, 1).swap())
    }

    @Test
    fun flattenNullable() {

        assertEquals(Pair(1, 1), Pair(1, 1).flattenNullable(0, 0))

        assertEquals(Pair(1, 0), Pair(1, null).flattenNullable(0, 0))
        assertEquals(Pair(0, 1), Pair(null, 1).flattenNullable(0, 0))

        assertEquals(Pair(0, 0), Pair(null, null).flattenNullable(0, 0))
    }

    @Test
    fun expandNullable() {
        val p: Pair<Int, Int>? = null
        assertEquals(Pair(null, null), p.expandNullable())
        assertEquals(Pair(1, 2), Pair(1, 2).expandNullable())
    }
}
