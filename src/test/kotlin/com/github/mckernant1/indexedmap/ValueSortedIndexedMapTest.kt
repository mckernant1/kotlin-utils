package com.github.mckernant1.indexedmap

import kotlin.random.Random
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
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
    fun testSameValue() {

        val m = ValueSortedIndexedMap<String, Int>()

        m["Hello"] = 1
        m["Hellno"] = 1

        assertEquals(1, m["Hello"])
        assertEquals(1, m["Hellno"])
        assertNull(m["asfsdf"])

        assertEquals(1, m.minValue()?.value)
        assertEquals(1, m.minValue()?.value)
        assertEquals(1, m.maxValue()?.value)
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

        assertEquals(3, m.minValue()!!.value)
        assertEquals(3, m.maxValue()?.value)
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

        assertEquals(1, m.minValue()!!.value)
        assertEquals(1, m.maxValue()?.value)
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
        assertNull(m.minValue())
        assertNull(m.maxValue())
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
        assertEquals(100_000, m.maxValue()?.value)
        assertEquals("Value-100000", m.maxValue()?.key)
        assertEquals(0, m.minValue()?.value)
        assertEquals("Value-0", m.minValue()?.key)

        assertMapConsistent(m)
    }

    @Test
    fun testAddingLotsOfValuesReversed() {

        val m = ValueSortedIndexedMap<String, Int>()

        (100_000 downTo 0).forEach {
            m["Value-$it"] = it
        }

        assertFalse(m.isEmpty())
        assertEquals(100_000, m.maxValue()?.value)
        assertEquals("Value-100000", m.maxValue()?.key)
        assertEquals(0, m.minValue()?.value)
        assertEquals("Value-0", m.minValue()?.key)

        assertMapConsistent(m)
    }

}
