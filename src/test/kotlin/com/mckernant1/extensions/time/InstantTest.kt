package com.mckernant1.extensions.time

import com.mckernant1.extensions.time.Instants.intervalsBetween
import com.mckernant1.extensions.time.Instants.timeSince
import com.mckernant1.extensions.time.Instants.timeUntil
import com.mckernant1.extensions.time.Instants.timeUntilNextWhole
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.Duration
import java.time.Instant
import java.time.temporal.ChronoUnit

internal class InstantTest {

    @Test
    fun testIntervalsBetween() {
        val x = Instant.now()
        assertEquals(
            x.intervalsBetween(x + Duration.ofHours(1), Duration.ofMinutes(10)),
            listOf(
                x,
                x + Duration.ofMinutes(10),
                x + Duration.ofMinutes(20),
                x + Duration.ofMinutes(30),
                x + Duration.ofMinutes(40),
                x + Duration.ofMinutes(50)
            )
        )

    }

    @Test
    fun testTimeUntilNext() {
        assertEquals(
            Duration.ofSeconds(2066).toMillis(),
            Instant.ofEpochSecond(1662852334).timeUntilNextWhole(ChronoUnit.HOURS).toMillis()
        )
    }

    @Test
    fun timeUntil() {
        val now = Instant.now()

        assertEquals(
            Duration.ofSeconds(30),
            now.timeUntil(now + Duration.ofSeconds(30))
        )

    }

    @Test
    fun timeSince() {
        val now = Instant.now()

        assertEquals(
            Duration.ofSeconds(30),
            now.timeSince(now - Duration.ofSeconds(30))
        )

    }

}
