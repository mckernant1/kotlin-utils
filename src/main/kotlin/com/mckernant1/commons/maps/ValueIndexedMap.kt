package com.mckernant1.commons.maps

/**
 * A map interface which created methods for returning all keys for a specific value
 */
interface ValueIndexedMap<K, V> : MutableMap<K, V> {

    fun getKeysForValue(value: V): Set<K>?

}
