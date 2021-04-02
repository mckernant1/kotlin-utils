package com.github.mckernant1.collections

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import kotlin.time.*

@ExperimentalTime
internal class CollectionExtensionsKtTest {

    @Test
    fun testCartesianProduct() {
        val l1 = listOf(1, 2)
        val l2 = listOf("a", "b")
        assertEquals(l1.cartesianProduct(l2), listOf(Pair(1, "a"), Pair(1, "b"), Pair(2, "a"), Pair(2, "b")))
    }

    @Test
    fun equalsPreserveOrder() {
        val l1 = listOf(1, 2)
        val l2 = listOf(2, 1)
        assertTrue(l1.equals(l2, preserveOrder = false))
        assertFalse(l1.equals(l2, preserveOrder = true))
    }

    @Test
    fun mapParallel() {
        val l1 = (1..10).toList()
        val timeTakenParallel = measureTime {
            l1.mapParallel {
                delay(1000)
            }
        }
        assertTrue(timeTakenParallel < (5).toDuration(DurationUnit.SECONDS))
        val timeTakenSequence = measureTime {
            l1.map {
                runBlocking {
                    delay(1000)
                }
            }
        }
        assertTrue(timeTakenSequence > (5).toDuration(DurationUnit.SECONDS))
    }

}
