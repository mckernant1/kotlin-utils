package com.github.mckernant1.extensions.math

import java.math.BigInteger

object IntAlgebra {

    /**
     * @return
     */
    fun Int.pow(exponent: Int): BigInteger = this.toBigInteger().pow(exponent)

    /**
     * @return The greatest common factor between the two values
     * @throws IllegalArgumentException if either number is negative
     */
    @Throws(IllegalArgumentException::class)
    fun Int.greatestCommonFactor(other: Int): Int = when {
        this < 0 || other < 0 -> throw IllegalArgumentException("Args cannot be negative, either $this or $other was negative")
        this > other -> if (this % other == 0) other else other.greatestCommonFactor(this % other)
        this < other -> if (other % this == 0) this else this.greatestCommonFactor(other % this)
        else -> this
    }

    /**
     * @return The least common multiple between the two values
     * @throws IllegalArgumentException if either number is negative
     */
    @Throws(IllegalArgumentException::class)
    fun Int.leastCommonMultiple(other: Int): Int = (this * other) / this.greatestCommonFactor(other)

}
