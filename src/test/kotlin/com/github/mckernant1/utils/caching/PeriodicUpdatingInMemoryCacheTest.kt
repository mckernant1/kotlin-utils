package com.github.mckernant1.utils.caching

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.Duration

internal class PeriodicUpdatingInMemoryCacheTest {

    @Test
    fun testUpdatingCache() {
        var x = 0
        val cache = PeriodicUpdatingInMemoryCache(Duration.ofMinutes(1)) {
            x += 1
            x
        }
        assertEquals(1, cache.getValue())
    }

}
