package com.mckernant1.commons.extensions.collections

object Iterator {

    /**
     * Cycles through an iterable indefinitely
     */
    fun <T> Iterable<T>.cycle(): Sequence<T> =
        generateSequence { this }.flatten()

}
