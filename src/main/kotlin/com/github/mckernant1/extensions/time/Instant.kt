package com.github.mckernant1.extensions.time

import java.time.Duration
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalAmount

fun Instant.elapsed(): Duration = Duration.between(this, Instant.now())

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

fun Instant.timeUntilNextWhole(unit: ChronoUnit): Duration = Duration.ofMillis(
    (this.truncatedTo(unit) + Duration.of(1, unit)).toEpochMilli()
            - this.toEpochMilli()
)
