package com.mckernant1.commons.extensions.time

import java.time.Duration
import java.time.temporal.Temporal

object Temporals {
    /**
     * @return the time until the other instant
     */
    fun Temporal.timeUntil(other: Temporal): Duration = Duration.between(this, other)

    /**
     * @return the time since the other instant
     */
    fun Temporal.timeSince(other: Temporal): Duration = Duration.between(other, this)

}
