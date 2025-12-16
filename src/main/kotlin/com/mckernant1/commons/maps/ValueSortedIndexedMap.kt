package com.mckernant1.commons.maps

import com.google.common.annotations.VisibleForTesting
import com.mckernant1.commons.extensions.collections.SortedMaps.firstEntryOrNull
import com.mckernant1.commons.extensions.collections.SortedMaps.lastEntryOrNull
import org.apache.commons.lang3.tuple.Pair
import java.util.SortedMap
import java.util.TreeMap
import kotlin.concurrent.read

/**
 * A map that has a sorted index based on the value attached to it.
 *
 * @param comparator the comparator to use for the values
 */
class ValueSortedIndexedMap<K, V>(
    comparator: Comparator<V> = Comparator.naturalOrder<V>(),
    fairLocking: Boolean = false
) : ValueIndexedDoubleMap<K, V>(
    fairLocking
), ValueSortedMap<K, V> where V : Comparable<V> {

    @VisibleForTesting
    override val baseMap: MutableMap<K, V> = HashMap()

    @VisibleForTesting
    override val index: SortedMap<V, MutableSet<K>> = TreeMap(comparator)

    override fun minValues(): Map.Entry<V, Set<K>>? = lock.read {
        index.firstEntryOrNull()?.let {
            Pair.of(it.key, it.value?.toSet() ?: emptySet())
        }
    }

    override fun maxValues(): Map.Entry<V, Set<K>>? = lock.read {
        index.lastEntryOrNull()?.let {
            Pair.of(it.key, it.value?.toSet() ?: emptySet())
        }
    }
}
