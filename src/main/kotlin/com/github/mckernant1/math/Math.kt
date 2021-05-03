package com.github.mckernant1.math

import java.math.BigDecimal
import java.math.RoundingMode

fun Double.round(digits: Int, roundingMode: RoundingMode = RoundingMode.HALF_EVEN): Double =
    BigDecimal(this).setScale(digits, roundingMode).toDouble()
