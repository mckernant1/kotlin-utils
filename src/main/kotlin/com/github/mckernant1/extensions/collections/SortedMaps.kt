package com.github.mckernant1.extensions.collections

import kotlin.jvm.Throws
import org.apache.commons.lang3.tuple.Pair
import java.util.NoSuchElementException
import java.util.SortedMap

object SortedMaps {

    @Throws(NoSuchElementException::class)
    fun <K, V> SortedMap<K, V>.firstEntry(): Map.Entry<K, V?> = Pair.of(this.firstKey(), this[this.firstKey()])

    fun <K, V> SortedMap<K, V>.firstKeyOrNull(): K? = try {
        this.firstKey()
    } catch (e: NoSuchElementException) {
        null
    }

    fun <K, V> SortedMap<K, V>.firstEntryOrNull(): Map.Entry<K, V?>? = this.firstKeyOrNull()?.let {
        Pair.of(it, this[it])
    }

    @Throws(NoSuchElementException::class)
    fun <K, V> SortedMap<K, V>.lastEntry(): Map.Entry<K, V?> = Pair.of(this.lastKey(), this[this.lastKey()])

    fun <K, V> SortedMap<K, V>.lastKeyOrNull(): K? = try {
        this.lastKey()
    } catch (e: NoSuchElementException) {
        null
    }

    fun <K, V> SortedMap<K, V>.lastEntryOrNull(): Map.Entry<K, V?>? = this.lastKeyOrNull()?.let {
        Pair.of(it, this[it])
    }

}
