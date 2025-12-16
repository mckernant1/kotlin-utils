package com.mckernant1.commons.maps

import kotlin.random.Random
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import java.util.Comparator
import java.util.UUID

class ValueSortedIndexedMapTest {


    private fun assertMapConsistent(map: ValueSortedIndexedMap<String, Int>) {
        val basePairs = map.baseMap.map { it.key to it.value }
        val indexPairs = map.index.flatMap { (i, sList) ->
            sList.map { it to i }
        }

        assertEquals(basePairs.map { it.first }.sorted(), indexPairs.map { it.first }.sorted())
        assertEquals(basePairs.map { it.second }.sorted(), indexPairs.map { it.second }.sorted())
    }

    @Test
    fun testCustomComparator() {
        // Use a reverse order comparator for values
        val m = ValueSortedIndexedMap<String, Int>(Comparator.reverseOrder())

        m["a"] = 1
        m["b"] = 2
        m["c"] = 3

        // With reverse comparator, the smallest according to the map is actually the largest natural value
        assertEquals(3, m.minValues()?.key)
        assertEquals(1, m.maxValues()?.key)

        assertMapConsistent(m)
    }

    @Test
    fun testSameValue() {

        val m = ValueSortedIndexedMap<String, Int>()

        m["Hello"] = 1
        m["Hellno"] = 1

        assertEquals(1, m["Hello"])
        assertEquals(1, m["Hellno"])
        assertNull(m["asfsdf"])

        assertEquals(1, m.minValues()?.key)
        assertEquals(1, m.minValues()?.key)
        assertEquals(1, m.maxValues()?.key)
        assertEquals(2, m.size)

        assertMapConsistent(m)

        assertEquals(1, m.remove("Hello"))

        assertMapConsistent(m)
    }

    @Test
    fun testSameKey() {

        val m = ValueSortedIndexedMap<String, Int>()
        m["Hello"] = 1
        m["Hello"] = 2
        m["Hello"] = 3

        assertEquals(3, m["Hello"])

        assertEquals(3, m.minValues()?.key)
        assertEquals(3, m.maxValues()?.key)
        assertEquals(1, m.size)

        assertMapConsistent(m)

        assertEquals(3, m.remove("Hello"))

        assertMapConsistent(m)
    }

    @Test
    fun testSameKeyAndSameValue() {
        val m = ValueSortedIndexedMap<String, Int>()
        m["Hello"] = 1
        m["Hello"] = 1
        m["Hello"] = 1

        assertEquals(1, m["Hello"])

        assertEquals(1, m.minValues()?.key)
        assertEquals(1, m.maxValues()?.key)
        assertEquals(1, m.size)

        assertMapConsistent(m)

        assertEquals(1, m.remove("Hello"))

        assertMapConsistent(m)

    }

    @Test
    fun testEmpty() {
        val m = ValueSortedIndexedMap<String, Int>()

        assertNull(m.remove("Hello"))
        assertNull(m["Hello"])
        assertNull(m.minValues())
        assertNull(m.maxValues())
        assertEquals(0, m.size)

        assertMapConsistent(m)
    }


    @Test
    fun testAddingLotsOfValuesRandomly() {

        val m = ValueSortedIndexedMap<String, Int>()

        repeat(100_000) {
            m[UUID.randomUUID().toString()] = Random.nextInt()
        }
        assertMapConsistent(m)
    }

    @Test
    fun testAddingLotsOfValues() {

        val m = ValueSortedIndexedMap<String, Int>()

        repeat(100_001) {
            m["Value-$it"] = it
        }

        assertFalse(m.isEmpty())
        assertEquals(100_000, m.maxValues()?.key)
        assertEquals("Value-100000", m.maxValues()?.value?.firstOrNull())
        assertEquals(0, m.minValues()?.key)
        assertEquals("Value-0", m.minValues()?.value?.firstOrNull())

        assertMapConsistent(m)
    }

    @Test
    fun testAddingLotsOfValuesReversed() {

        val m = ValueSortedIndexedMap<String, Int>()

        (100_000 downTo 0).forEach {
            m["Value-$it"] = it
        }

        assertFalse(m.isEmpty())
        assertEquals(100_000, m.maxValues()?.key)
        assertEquals("Value-100000", m.maxValues()?.value?.firstOrNull())
        assertEquals(0, m.minValues()?.key)
        assertEquals("Value-0", m.minValues()?.value?.firstOrNull())

        assertMapConsistent(m)
    }

}
