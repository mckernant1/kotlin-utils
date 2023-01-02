package com.github.mckernant1.extensions.apache

import org.apache.commons.lang3.tuple.Pair

object Pair {

    fun <A, B> Pair<A, B>.swap(): Pair<B, A> = Pair.of(this.right, this.left)

    fun <A, B> Pair<A?, B?>.flattenNullable(
        defaultA: A,
        defaultB: B
    ): Pair<A, B>? = when {
        left == null && right == null -> null
        left == null && right != null -> Pair.of(defaultA, right!!)
        left != null && right == null -> Pair.of(left!!, defaultB)
        else -> Pair.of(left!!, right!!)
    }

    fun <A, B> Pair<A, B>?.expandNullable(): Pair<A?, B?> = when(this) {
        null -> Pair.of(null, null)
        else -> Pair.of(left, right)
    }

}
