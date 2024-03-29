package com.mckernant1.commons.extensions.math

import java.text.DecimalFormat

object DoubleFormat {
    fun Double.format(pattern: String = "0.00"): String =
        DecimalFormat(pattern).format(this)
}

