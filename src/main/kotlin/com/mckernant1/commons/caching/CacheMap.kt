package com.mckernant1.commons.caching

/**
 * Simple in memory cache interface
 */
interface CacheMap<K, V> {

    operator fun set(key: K, value: V)

    operator fun get(key: K): V?

    val size: Int

}
