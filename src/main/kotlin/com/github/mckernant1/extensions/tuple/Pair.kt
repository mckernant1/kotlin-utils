package com.github.mckernant1.extensions.tuple

import kotlin.Pair

object Pair {

    fun <A, B> Pair<A, B>.swap(): Pair<B, A> = Pair(this.second, this.first)

    fun <A, B> Pair<A, B>.toEntry(): Map.Entry<A, B> = org.apache.commons.lang3.tuple.Pair.of(this.first, this.second)

    fun <A, B> Pair<A?, B?>.flattenNullable(
        defaultA: A,
        defaultB: B
    ): Pair<A, B>? = when {
        first == null && second == null -> null
        first == null && second != null -> Pair(defaultA, second!!)
        first != null && second == null -> Pair(first!!, defaultB)
        else -> Pair(first!!, second!!)
    }


    fun <A, B> Pair<A, B>?.expandNullable(): Pair<A?, B?> = when(this) {
        null -> Pair(null, null)
        else -> Pair(first, second)
    }

}


