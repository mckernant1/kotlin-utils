package com.mckernant1.extensions.time

import java.time.Duration
import java.time.Instant
import java.time.temporal.TemporalAmount
import java.time.temporal.TemporalUnit

object Instants {
    fun Instant.elapsed(): Duration = Duration.between(this, Instant.now())

    /**
     * start inclusive, end exclusive
     */
    fun Instant.intervalsBetween(
        end: Instant,
        interval: TemporalAmount
    ): List<Instant> {
        val instants: MutableList<Instant> = mutableListOf()
        var counter = this
        while (counter.isBefore(end)) {
            instants.add(counter)
            counter += interval
        }
        return instants
    }

    fun Instant.isBeforeNow(): Boolean = this.isBefore(Instant.now())

    fun Instant.isAfterNow(): Boolean = this.isAfter(Instant.now())

    /**
     * @return the time until the other instant
     */
    fun Instant.timeUntil(other: Instant): Duration = Duration.between(this, other)

    /**
     * @return the time since the other instant
     */
    fun Instant.timeSince(other: Instant): Duration = Duration.between(other, this)

    /**
     * For example if we are at time 2:30, and the unit is HOURS
     * the time until next whole hours is 30 minutes
     *
     * @return the time until the next whole unit
     */
    fun Instant.timeUntilNextWhole(unit: TemporalUnit): Duration =
        (this.truncatedTo(unit) + Duration.of(1, unit)).timeSince(this)

}
