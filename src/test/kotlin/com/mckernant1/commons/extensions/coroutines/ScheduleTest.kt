package com.mckernant1.commons.extensions.coroutines

import com.mckernant1.commons.extensions.coroutines.Schedule.schedule
import com.mckernant1.commons.extensions.coroutines.Schedule.scheduleAtFixedRate
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.Duration
import java.util.concurrent.atomic.AtomicInteger

@OptIn(ExperimentalCoroutinesApi::class)
class ScheduleTest {

    @Test
    fun testSchedule() = runTest {
        val counter = AtomicInteger(0)
        val delay = Duration.ofMillis(100)

        schedule(delay) {
            counter.incrementAndGet()
        }

        runCurrent()
        assertEquals(0, counter.get(), "Should execute block immediately")

        advanceTimeBy(delay.toMillis() + 1)
        assertEquals(1, counter.get(), "Should still be 1")
    }

    @Test
    fun testScheduleAtFixedRate() = runTest {
        val counter = AtomicInteger(0)
        val initialDelay = Duration.ofMillis(100)
        val period = Duration.ofMillis(200)

        val job = scheduleAtFixedRate(initialDelay, period) {
            counter.incrementAndGet()
        }

        runCurrent()
        assertEquals(0, counter.get(), "Should not execute before initial delay")

        advanceTimeBy(initialDelay.toMillis())
        runCurrent()
        assertEquals(1, counter.get(), "Should execute after initial delay")

        advanceTimeBy(period.toMillis())
        runCurrent()
        assertEquals(2, counter.get(), "Should execute after first period")

        advanceTimeBy(period.toMillis())
        runCurrent()
        assertEquals(3, counter.get(), "Should execute after second period")

        job.cancel()
    }
}
