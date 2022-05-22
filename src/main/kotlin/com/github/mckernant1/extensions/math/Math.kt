package com.github.mckernant1.extensions.math

import kotlin.math.floor
import kotlin.math.pow
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat

fun Double.round(digits: Int, roundingMode: RoundingMode = RoundingMode.HALF_EVEN): Double =
    BigDecimal(this).setScale(digits, roundingMode).toDouble()

fun Long.pow(exponent: Int): Long = toBigInteger().pow(exponent).toLong()

fun Int.pow(exponent: Int): Int = toBigInteger().pow(exponent).toInt()

fun Double.isInt(): Boolean = floor(this) == this

fun Double.format(pattern: String = "0.00"): String =
    DecimalFormat(pattern).format(this)

