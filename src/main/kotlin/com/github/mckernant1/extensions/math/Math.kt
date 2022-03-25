package com.github.mckernant1.extensions.math

import kotlin.math.floor
import kotlin.math.pow
import java.math.BigDecimal
import java.math.RoundingMode

fun Double.round(digits: Int, roundingMode: RoundingMode = RoundingMode.HALF_EVEN): Double =
    BigDecimal(this).setScale(digits, roundingMode).toDouble()

fun Long.pow(exponent: Int): Long = toDouble().pow(exponent).toLong()

fun Int.pow(exponent: Int): Int = toDouble().pow(exponent).toInt()

fun Double.isInt(): Boolean = floor(this) == this
