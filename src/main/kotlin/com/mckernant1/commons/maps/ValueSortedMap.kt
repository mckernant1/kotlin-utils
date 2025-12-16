package com.mckernant1.commons.maps

/**
 * A map implementation that adds methods to get the minimum and maximum entries by value.
 */
interface ValueSortedMap<K, V> : MutableMap<K, V> where V : Comparable<V> {

    /**
     * @return the min entries by value
     */
    fun minValues(): Map.Entry<V, Set<K>>?

    /**
     * @return the max entries by value
     */
    fun maxValues(): Map.Entry<V, Set<K>>?

}
