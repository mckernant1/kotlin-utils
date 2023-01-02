package com.github.mckernant1.extensions.collections

import com.github.mckernant1.extensions.collections.SortedMaps.firstEntryOrNull
import com.github.mckernant1.extensions.collections.SortedMaps.firstKeyOrNull
import com.github.mckernant1.extensions.collections.SortedMaps.lastEntryOrNull
import com.github.mckernant1.extensions.collections.SortedMaps.lastKeyOrNull
import com.github.mckernant1.extensions.tuple.Pair.toEntry
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import java.util.TreeMap

class SortedMapsTest {

    @Test
    fun baseEntry() {

        val m = TreeMap<Int, String>()
        m[1] = "Hello"

        assertEquals(Pair(1, "Hello").toEntry(), m.firstEntry())
        assertEquals(Pair(1, "Hello").toEntry(), m.lastEntry())
    }

    @Test
    fun baseEntryOrNull() {
        val m = TreeMap<Int, String>()

        assertNull(m.firstEntryOrNull())
        assertNull(m.lastEntryOrNull())

        m[1] = "Hello"

        assertEquals(Pair(1, "Hello").toEntry(), m.firstEntryOrNull())
        assertEquals(Pair(1, "Hello").toEntry(), m.lastEntryOrNull())

    }

    @Test
    fun keysOrNull() {

        val m = TreeMap<Int, String>()

        assertNull(m.firstKeyOrNull())
        assertNull(m.lastKeyOrNull())

        m[1] = "Hello"

        assertEquals(1, m.firstKeyOrNull())
        assertEquals(1, m.lastKeyOrNull())

    }


}
