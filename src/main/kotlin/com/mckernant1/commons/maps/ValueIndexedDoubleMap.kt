package com.mckernant1.commons.maps

import com.google.common.annotations.VisibleForTesting
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write

/**
 * A value indexed map
 *
 * It is important that none of the values gotten from this data structure are mutable.
 *
 * All methods in this class return copies
 *
 * This implementation uses locks around puts/removes, but not gets.
 * It also uses synchronized implementations of hashmap and treemap
 *
 *
 */
open class ValueIndexedDoubleMap<K, V>(
    fairLocking: Boolean = false,
) : ValueIndexedMap<K, V> {

    @VisibleForTesting
    internal open val baseMap: MutableMap<K, V> = HashMap()

    @VisibleForTesting
    internal open val index: MutableMap<V, MutableSet<K>> = HashMap()

    protected open val lock: ReentrantReadWriteLock = ReentrantReadWriteLock(fairLocking)

    override fun get(key: K): V? = lock.read { baseMap[key] }

    override val entries: MutableSet<MutableMap.MutableEntry<K, V>>
        get() = baseMap.entries.toMutableSet()

    override val keys: MutableSet<K>
        get() = baseMap.keys.toMutableSet()

    override val size: Int
        get() = baseMap.size

    override val values: MutableCollection<V>
        get() = baseMap.values.toMutableList()

    override fun clear() {
        lock.write {
            baseMap.clear()
            index.clear()
        }
    }


    override fun isEmpty(): Boolean = lock.read { baseMap.isEmpty() }

    private fun removeUnsafe(key: K): V? {
        val v = baseMap.remove(key) ?: return null

        val keys = index[v] ?: throw IllegalStateException("A Key in the base map is not present in the index")

        keys.remove(key)

        if (keys.isEmpty()) {
            index.remove(v)
        } else {
            index[v] = keys
        }
        return v
    }

    override fun remove(key: K): V? {
        return lock.write { removeUnsafe(key) }
    }

    override fun putAll(from: Map<out K, V>) {
        from.forEach(this::put)
    }

    override fun put(key: K, value: V): V? {
        return lock.write {
            removeUnsafe(key)
            val old = baseMap.put(key, value)

            val keys = index[value] ?: mutableSetOf()
            keys.add(key)
            index[value] = keys
            return@write old
        }
    }

    override fun containsValue(value: V): Boolean = lock.read { index.containsKey(value) }

    override fun containsKey(key: K): Boolean = lock.read { baseMap.containsKey(key) }

    override fun getKeysForValue(value: V): Set<K>? = lock.read { index[value]?.toSet() }

}
