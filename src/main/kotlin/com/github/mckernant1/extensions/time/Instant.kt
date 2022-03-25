package com.github.mckernant1.extensions.time

import java.time.Duration
import java.time.Instant
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
