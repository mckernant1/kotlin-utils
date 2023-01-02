package com.github.mckernant1.indexedmap

import com.github.mckernant1.extensions.collections.SortedMaps.firstEntry
import com.github.mckernant1.extensions.collections.SortedMaps.lastEntry
import com.github.mckernant1.extensions.tuple.Pair.toEntry
import com.google.common.annotations.VisibleForTesting
import kotlin.concurrent.withLock
import java.util.Collections
import java.util.SortedMap
import java.util.TreeMap
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.locks.ReentrantLock

/**
 * A map that has a sorted index based on the value attached to it.
 *
 * It is important that none of the values gotten from this data structure are mutable.
 * Any Mutation will require a remove + put to keep the index working
 *
 * This implementation uses locks around puts/removes, but not gets.
 * It also uses synchronized implementations of hashmap and treemap
 */
class ValueSortedIndexedMap<K, V> : MutableMap<K, V> where V : Comparable<V> {

    @VisibleForTesting
    internal val baseMap: MutableMap<K, V> = ConcurrentHashMap()

    @VisibleForTesting
    internal val index: SortedMap<V, MutableSet<K>> = Collections.synchronizedSortedMap(TreeMap())

    private val lock: ReentrantLock = ReentrantLock()

    override fun get(key: K): V? = baseMap[key]

    override val entries: MutableSet<MutableMap.MutableEntry<K, V>>
        get() = baseMap.entries

    override val keys: MutableSet<K>
        get() = baseMap.keys

    override val size: Int
        get() = baseMap.size

    override val values: MutableCollection<V>
        get() = baseMap.values

    override fun clear() {
        lock.withLock {
            baseMap.clear()
            index.clear()
        }
    }


    override fun isEmpty(): Boolean = baseMap.isEmpty()
    override fun remove(key: K): V? {
        return lock.withLock {
            val v = baseMap.remove(key) ?: return null

            val keys = index[v] ?: throw RuntimeException("A Key in the base map is not present in the index")

            keys.remove(key)

            if (keys.isEmpty()) {
                index.remove(v)
            } else {
                index[v] = keys
            }
            return@withLock v
        }
    }

    override fun putAll(from: Map<out K, V>) {
        from.forEach(this::put)
    }

    override fun put(key: K, value: V): V? {
        remove(key)

        return lock.withLock {
            val old = baseMap.put(key, value)

            val keys = index[value] ?: mutableSetOf()
            keys.add(key)
            index[value] = keys
            return@withLock old
        }
    }

    override fun containsValue(value: V): Boolean = index.containsKey(value)

    override fun containsKey(key: K): Boolean = baseMap.containsKey(key)

    fun minValue(): Map.Entry<K, V>? = if (index.isNotEmpty()) {
        index.firstEntry().let {
            it.value!!.first() to it.key
        }.toEntry()
    } else {
        null
    }

    fun maxValue(): Map.Entry<K, V>? = if (index.isNotEmpty()) {
        index.lastEntry().let {
            it.value!!.first() to it.key
        }.toEntry()
    } else {
        null
    }
}
