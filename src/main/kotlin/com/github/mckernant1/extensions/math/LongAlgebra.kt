package com.github.mckernant1.extensions.math

import java.math.BigInteger

object LongAlgebra {
    fun Long.pow(exponent: Int): BigInteger = this.toBigInteger().pow(exponent)

    /**
     * @return The greatest common factor between the two values
     * @throws IllegalArgumentException if either number is negative
     */
    @Throws(IllegalArgumentException::class)
    fun Long.greatestCommonFactor(other: Long): Long = when {
        this < 0 || other < 0 -> throw IllegalArgumentException("Args cannot be negative, either $this or $other was negative")
        this > other -> if (this % other == 0L) other else other.greatestCommonFactor(this % other)
        this < other -> if (other % this == 0L) this else this.greatestCommonFactor(other % this)
        else -> this
    }

    /**
     * @return The least common multiple between the two values
     * @throws IllegalArgumentException if either number is negative
     */
    @Throws(IllegalArgumentException::class)
    fun Long.leastCommonMultiple(other: Long): Long = (this * other) / this.greatestCommonFactor(other)
}
