package com.mckernant1.extensions.collections

object Iterator {

    /**
     * Cycles through an iterable indefinitely
     */
    fun <T> Iterable<T>.cycle() =
        generateSequence { this }.flatten()

}
