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
 * All collections methods in this class return copies. Get does not return a copy.
 *
 * This implementation uses locks around puts/removes, but not gets.
 * It also uses synchronized implementations of hashmap and treemap
 *
 * Collections methods are slow because of copies.
 * Ideally this Collection should be used for inserts and reads from the base map and index.
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
        get() = lock.read { baseMap.entries.toMutableSet() }

    override val keys: MutableSet<K>
        get() = lock.read { baseMap.keys.toMutableSet() }

    override val size: Int
        get() = lock.read { baseMap.size }

    override val values: MutableCollection<V>
        get() = lock.read { baseMap.values.toMutableList() }

    override fun clear(): Unit = lock.write {
        baseMap.clear()
        index.clear()
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

    private fun putUnsafe(key: K, value: V): V? {
        removeUnsafe(key)
        val old = baseMap.put(key, value)

        val keys = index[value] ?: mutableSetOf()
        keys.add(key)
        index[value] = keys
        return old
    }

    override fun remove(key: K): V? = lock.write { removeUnsafe(key) }

    override fun putAll(from: Map<out K, V>): Unit = lock.write { from.forEach(this::putUnsafe) }

    override fun put(key: K, value: V): V? = lock.write { putUnsafe(key, value) }

    override fun containsValue(value: V): Boolean = lock.read { index.containsKey(value) }

    override fun containsKey(key: K): Boolean = lock.read { baseMap.containsKey(key) }

    override fun getKeysForValue(value: V): Set<K>? = lock.read { index[value]?.toSet() }

}
