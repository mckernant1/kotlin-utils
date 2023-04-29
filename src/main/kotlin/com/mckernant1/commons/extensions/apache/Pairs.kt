package com.mckernant1.commons.extensions.apache

import org.apache.commons.lang3.tuple.Pair

object Pairs {

    fun <A, B> Pair<A, B>.swap(): Pair<B, A> = Pair.of(this.right, this.left)

    /**
     * If neither are null return the pair
     * Otherwise replace with the default values
     */
    fun <A, B> Pair<A?, B?>.flattenNullable(
        defaultA: A,
        defaultB: B
    ): Pair<A, B> = when {
        left == null && right == null -> Pair.of(defaultA, defaultB)
        left == null && right != null -> Pair.of(defaultA, right!!)
        left != null && right == null -> Pair.of(left!!, defaultB)
        else -> Pair.of(left!!, right!!)
    }

    fun <A, B> Pair<A, B>?.expandNullable(): Pair<A?, B?> = when(this) {
        null -> Pair.of(null, null)
        else -> Pair.of(left, right)
    }

}
