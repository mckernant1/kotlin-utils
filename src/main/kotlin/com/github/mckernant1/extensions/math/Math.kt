package com.github.mckernant1.extensions.math

import kotlin.math.floor
import kotlin.math.pow
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat

fun Double.round(digits: Int, roundingMode: RoundingMode = RoundingMode.HALF_EVEN): Double =
    BigDecimal(this).setScale(digits, roundingMode).toDouble()

fun <T> T.pow(exponent: Int): Double
        where T : Number = this.toDouble().pow(exponent)

fun Double.isInt(): Boolean = floor(this) == this

fun Double.format(pattern: String = "0.00"): String =
    DecimalFormat(pattern).format(this)

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
