package com.github.mckernant1.extensions.map

import org.apache.commons.lang3.tuple.Pair

object Entry {

    fun <K, V> Map.Entry<K, V>.swap(): Map.Entry<V, K> = Pair.of(this.value, this.key)

}
