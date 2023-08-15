package com.mckernant1.commons.caching

import java.util.LinkedHashMap

/**
 * LRU cache implementation with **[LinkedHashMap]**.
 * More of an alias with a better name then anything else.
 */
class LRUCache<K, V>(
    private val maxSize: Int
) : LinkedHashMap<K, V>(maxSize, 0.75f, true), CacheMap<K, V> {

    override fun removeEldestEntry(eldest: MutableMap.MutableEntry<K, V>?): Boolean {
        return size > maxSize
    }

    override fun set(key: K, value: V) {
        super.put(key, value)
    }

    override val size: Int
        get() = super.size

}
