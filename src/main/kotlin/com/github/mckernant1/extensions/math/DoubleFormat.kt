package com.github.mckernant1.extensions.math

import java.text.DecimalFormat

object DoubleFormat {
    fun Double.format(pattern: String = "0.00"): String =
        DecimalFormat(pattern).format(this)
}

