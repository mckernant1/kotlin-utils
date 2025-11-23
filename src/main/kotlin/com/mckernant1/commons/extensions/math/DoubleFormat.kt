package com.mckernant1.commons.extensions.math

import java.text.DecimalFormat
import java.text.NumberFormat

object DoubleFormat {
    fun Double.format(format: NumberFormat = DecimalFormat("0.00")): String =
        format.format(this)
}

