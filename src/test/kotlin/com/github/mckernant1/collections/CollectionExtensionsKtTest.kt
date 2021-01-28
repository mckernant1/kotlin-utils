package com.github.mckernant1.collections

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class CollectionExtensionsKtTest {

    @Test
    fun testCartesianProduct() {
        val l1 = listOf(1, 2)
        val l2 = listOf("a", "b".equals("Asdf", ignoreCase = true))
        println(l1.cartesianProduct(l2) == listOf(Pair(1, "a"), Pair(2, "a"), Pair(1, "b"), Pair(2, "b")))
        assertEquals(l1.cartesianProduct(l2), listOf(Pair(1, "a"), Pair(2, "a"), Pair(1, "b"), Pair(2, "b")))
    }

    @Test
    fun equalsPreserveOrder() {
        val l1 = listOf(1, 2)
        val l2 = listOf(2, 1)
        assertTrue(l1.equals(l2, preserveOrder = false))
        assertFalse(l1.equals(l2, preserveOrder = true))
    }

}
