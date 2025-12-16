package com.mckernant1.commons.maps

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ValueIndexedDoubleMapTest {

    private fun <K, V> assertMapConsistent(map: ValueIndexedDoubleMap<K, V>) {
        val basePairs = map.baseMap.map { it.key to it.value }
        val indexPairs = map.index.flatMap { (v, keys) ->
            keys.map { it to v }
        }

        assertEquals(basePairs.map { it.first }.sortedBy { it.hashCode() }, indexPairs.map { it.first }.sortedBy { it.hashCode() })
        assertEquals(basePairs.map { it.second }.sortedBy { it.hashCode() }, indexPairs.map { it.second }.sortedBy { it.hashCode() })
        assertEquals(map.size, map.baseMap.size)
    }

    @Test
    fun testEmpty() {
        val m = ValueIndexedDoubleMap<String, Int>()

        assertTrue(m.isEmpty())
        assertEquals(0, m.size)
        assertNull(m["missing"])
        assertFalse(m.containsKey("missing"))
        assertFalse(m.containsValue(1))
        assertNull(m.getKeysForValue(1))
        assertMapConsistent(m)
    }

    @Test
    fun testPutAndGet() {
        val m = ValueIndexedDoubleMap<String, Int>()

        assertNull(m.put("a", 1))
        assertEquals(1, m["a"])
        assertTrue(m.containsKey("a"))
        assertTrue(m.containsValue(1))
        assertEquals(setOf("a"), m.getKeysForValue(1))
        assertMapConsistent(m)
    }

    @Test
    fun testMultipleKeysSameValue() {
        val m = ValueIndexedDoubleMap<String, Int>()

        m["a"] = 1
        m["b"] = 1

        assertEquals(2, m.size)
        assertEquals(setOf("a", "b"), m.getKeysForValue(1))
        assertMapConsistent(m)
    }

    @Test
    fun testUpdateSameKeyDifferentValues() {
        val m = ValueIndexedDoubleMap<String, Int>()

        m["a"] = 1
        m["a"] = 2

        assertEquals(1, m.size)
        assertEquals(2, m["a"])
        assertNull(m.getKeysForValue(1))
        assertEquals(setOf("a"), m.getKeysForValue(2))
        assertMapConsistent(m)
    }

    @Test
    fun testRemoveKey() {
        val m = ValueIndexedDoubleMap<String, Int>()

        m["a"] = 1
        m["b"] = 2
        m["c"] = 2

        assertEquals(1, m.remove("a"))
        assertNull(m["a"])
        assertFalse(m.containsKey("a"))
        assertNull(m.getKeysForValue(1))
        assertEquals(setOf("b", "c"), m.getKeysForValue(2))
        assertNull(m.remove("a"))
        assertMapConsistent(m)
    }

    @Test
    fun testPutAll() {
        val m = ValueIndexedDoubleMap<String, Int>()

        m["a"] = 1

        m.putAll(mapOf(
            "a" to 3, // overwrite
            "b" to 3,
            "c" to 4
        ))

        assertEquals(3, m["a"])
        assertEquals(setOf("a", "b"), m.getKeysForValue(3))
        assertEquals(setOf("c"), m.getKeysForValue(4))
        assertMapConsistent(m)
    }

    @Test
    fun testClear() {
        val m = ValueIndexedDoubleMap<String, Int>()

        m["a"] = 1
        m["b"] = 2
        m.clear()

        assertEquals(0, m.size)
        assertTrue(m.isEmpty())
        assertNull(m.getKeysForValue(1))
        assertMapConsistent(m)
    }

    @Test
    fun testContainsKeyAndValue() {
        val m = ValueIndexedDoubleMap<String, Int>()

        m["a"] = 1
        assertTrue(m.containsKey("a"))
        assertFalse(m.containsKey("b"))
        assertTrue(m.containsValue(1))
        assertFalse(m.containsValue(2))
        assertMapConsistent(m)
    }

    @Test
    fun testGetKeysForValueReturnsCopy() {
        val m = ValueIndexedDoubleMap<String, Int>()

        m["a"] = 1
        m["b"] = 1

        val keys = m.getKeysForValue(1)
        assertEquals(setOf("a", "b"), keys)

        // Attempt to mutate returned set should not affect internal index
        keys as MutableSet
        keys.remove("a")

        // Map should remain unchanged
        assertEquals(setOf("a", "b"), m.getKeysForValue(1))
        assertMapConsistent(m)
    }
}
