package com.mckernant1.commons.extensions.math

import kotlin.math.floor
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode

object DoubleAlgebra  {

    fun Double.pow(exponent: Int): BigDecimal = this.toBigDecimal().pow(exponent)

    /**
     * Care the double. This will not work exactly as predicted because double
     * Mostly useful for string formatting
     *
     * https://stackoverflow.com/questions/47781393/java-roundingmode-half-up-doesnt-round-up-as-expected
     */
    fun Double.round(digits: Int, roundingMode: RoundingMode = RoundingMode.HALF_UP): Double =
        BigDecimal(this).round(MathContext(digits, roundingMode)).toDouble()

    fun Double.isInt(): Boolean = floor(this) == this

}
