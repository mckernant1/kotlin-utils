package com.mckernant1.commons.standalone

import com.mckernant1.commons.standalone.Measure.measureDuration
import com.mckernant1.commons.standalone.Measure.measureOperation
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.time.Duration

internal class TimeTest {

    @Test
    fun testMeasureDuration() {
        val x = measureDuration {
            1 + 2
        }
        assertTrue(x > Duration.ofSeconds(0))
    }

    @Test
    fun testMeasureOperation() {
        val x: TimedOperation<Int> = measureOperation {
            1 + 2
        }
        assertEquals(x.result, 3)
        assertTrue(x.duration > Duration.ofSeconds(0))
    }
}
