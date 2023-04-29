package com.mckernant1.commons.extensions.math

import org.apache.commons.math3.stat.descriptive.rank.Percentile

object Percentiles {
    /**
     * Percentiles of a list. Maybe a bit off due to double precision
     * Can also be used for quantiles by using a decimal value
     */
    fun <I, E> Iterable<E>.percentile(p: I): Double
            where E : Number,
                  I : Number = Percentile(p.toDouble()).evaluate(this.map { it.toDouble() }.toDoubleArray())
}
