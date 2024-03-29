package com.mckernant1.commons.extensions.map

import org.apache.commons.lang3.tuple.Pair

object Entry {

    fun <K, V> Map.Entry<K, V>.swap(): Map.Entry<V, K> = Pair.of(this.value, this.key)

    fun <K, V> Map.Entry<K, V>.toPair(): kotlin.Pair<K, V> = Pair(key, value)

}
