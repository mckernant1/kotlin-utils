package com.mckernant1.commons.maps

/**
 * A map interface which created methods for returning all keys for a specific value
 */
interface ValueIndexedMap<K, V> : MutableMap<K, V> {

    /**
     * @param value to get the keys for
     * @return null if there are no keys for the given value
     */
    fun getKeysForValue(value: V): Set<K>?

}
