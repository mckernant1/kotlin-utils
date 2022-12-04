package com.github.mckernant1.extensions.collections

import com.github.mckernant1.extensions.collections.SetTheory.cartesianProduct
import com.github.mckernant1.extensions.collections.SetTheory.except
import com.github.mckernant1.extensions.collections.SetTheory.exceptBy
import com.github.mckernant1.extensions.collections.SetTheory.intersect
import com.github.mckernant1.extensions.collections.SetTheory.intersectBy
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


internal class CollectionExtensionsTest {

    @Test
    fun testCartesianProduct() {
        val l1 = listOf(1, 2)
        val l2 = listOf("a", "b")
        assertEquals(l1.cartesianProduct(l2), listOf(Pair(1, "a"), Pair(1, "b"), Pair(2, "a"), Pair(2, "b")))
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
