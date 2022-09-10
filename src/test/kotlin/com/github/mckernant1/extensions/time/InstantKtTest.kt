package com.github.mckernant1.extensions.time

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.Duration
import java.time.Instant
import java.time.temporal.ChronoUnit

internal class InstantKtTest {

    @Test
    fun testTimeUntilNext() {
        assertEquals(
            Duration.ofSeconds(2066).toMillis(),
            Instant.ofEpochSecond(1662852334).timeUntilNextWhole(ChronoUnit.HOURS).toMillis()
        )
    }

}
