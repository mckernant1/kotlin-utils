package com.github.mckernant1.utils.caching

import org.junit.jupiter.api.Assertions.assertTrue
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
        val s = generateSequence {
            cache.getValue()
        }.take(10).toList()
        assertTrue(s.distinct().size == 1)
    }

}
