package com.github.mckernant1.extensions.collections

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test


internal class CollectionExtensionsTest {

    @Test
    fun testCartesianProduct() {
        val l1 = listOf(1, 2)
        val l2 = listOf("a", "b")
        assertEquals(l1.cartesianProduct(l2), listOf(Pair(1, "a"), Pair(1, "b"), Pair(2, "a"), Pair(2, "b")))
    }

    @Test
    fun equalsPreserveOrder() {
        val l1 = listOf(1, 2)
        val l2 = listOf(2, 1)
        assertTrue(l1.equals(l2, preserveOrder = false))
        assertFalse(l1.equals(l2, preserveOrder = true))
    }


    @Test
    fun intersect() {
        val l1 = listOf(1, 2)
        val l2 = listOf(2, 3)

        assertEquals(l1.intersect(l2), listOf(2))
    }

    @Test
    fun except() {
        val l1 = listOf(1, 2)
        val l2 = listOf(2, 3)

        assertEquals(l1.except(l2), listOf(1))
    }

    @Test
    fun intersectBy() {
        val l1 = listOf(TestClass(1), TestClass(2))
        val l2 = listOf(TestClass(2), TestClass(3))

        assertEquals(listOf(TestClass(2)), l1.intersectBy(l2) { it.i })
    }

    @Test
    fun exceptBy() {
        val l1 = listOf(TestClass(1), TestClass(2))
        val l2 = listOf(TestClass(2), TestClass(3))

        assertEquals(listOf(TestClass(1)), l1.exceptBy(l2) { it.i })
    }


    private data class TestClass(
        val i: Int
    )
}
