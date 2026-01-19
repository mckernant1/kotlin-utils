package com.mckernant1.commons.caching

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.concurrent.atomic.AtomicInteger

class SuspendCacheTest {

    @Test
    fun testGetCachesValue(): Unit = runBlocking {
        val loaderCallCount = AtomicInteger(0)
        val cache = SuspendCache<String, String>(this) {
            loaderCallCount.incrementAndGet()
            "value for $it"
        }

        assertEquals("value for key1", cache["key1"])
        assertEquals("value for key1", cache["key1"])
        assertEquals(1, loaderCallCount.get(), "Loader should only be called once for the same key")
    }

    @Test
    fun testInvalidateRemovesValue(): Unit = runBlocking {
        val loaderCallCount = AtomicInteger(0)
        val cache = SuspendCache<String, String>(this) {
            loaderCallCount.incrementAndGet()
            "value for $it"
        }

        cache["key1"]
        cache.invalidate("key1")
        cache["key1"]

        assertEquals(2, loaderCallCount.get(), "Loader should be called again after invalidation")
    }

    @Test
    fun testConcurrentAccessToSameKey(): Unit = runBlocking {
        val loaderCallCount = AtomicInteger(0)
        val cache = SuspendCache<String, String>(this) {
            delay(100)
            loaderCallCount.incrementAndGet()
            "value"
        }

        val deferred1 = async { cache["key1"] }
        val deferred2 = async { cache["key1"] }

        assertEquals("value", deferred1.await())
        assertEquals("value", deferred2.await())
        assertEquals(1, loaderCallCount.get(), "Loader should only be called once for concurrent requests for the same key")
    }

    @Test
    fun testConcurrentAccessToDifferentKeys(): Unit = runBlocking {
        val loaderCallCount = AtomicInteger(0)
        val cache = SuspendCache<String, String>(this) {
            delay(100)
            loaderCallCount.incrementAndGet()
            "value for $it"
        }

        val deferred1 = async { cache["key1"] }
        val deferred2 = async { cache["key2"] }

        assertEquals("value for key1", deferred1.await())
        assertEquals("value for key2", deferred2.await())
        assertEquals(2, loaderCallCount.get(), "Loader should be called for each unique key")
    }

    @Test
    fun testInvalidateDuringLoading(): Unit = runBlocking {
        val loaderCallCount = AtomicInteger(0)
        val mutex = Mutex(locked = true)
        val cache = SuspendCache<String, String>(this) {
            loaderCallCount.incrementAndGet()
            mutex.withLock { "value" }
        }

        val deferred1 = async { cache["key1"] }

        // Wait a bit to ensure loader started
        delay(50)

        cache.invalidate("key1")
        mutex.unlock()

        try {
            deferred1.await()
        } catch (e: Exception) {
            // Depending on how cancel() behaves with the deferred, it might throw
        }

        // Now load again
        val result = cache["key1"]
        assertEquals("value", result)
        assertEquals(2, loaderCallCount.get(), "Should have reloaded after invalidation during load")
    }

    @Test
    fun testExceptionInLoader(): Unit = runBlocking {
        val loaderCallCount = AtomicInteger(0)
        // Use a supervisor scope or a separate scope to prevent the test scope from being cancelled
        val cache = SuspendCache<String, String>(kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.SupervisorJob())) {
            loaderCallCount.incrementAndGet()
            throw RuntimeException("loader error")
        }

        try {
            cache["key1"]
        } catch (e: Exception) {
            // Unwrap if necessary or check message
            val message = e.message ?: e.cause?.message
            assertEquals("loader error", message)
        }

        // Try again, it should still be in cache as a failed deferred
        try {
            cache["key1"]
        } catch (e: Exception) {
            val message = e.message ?: e.cause?.message
            assertEquals("loader error", message)
        }

        assertEquals(1, loaderCallCount.get(), "Loader should NOT be called again if it previously failed (cached failure)")

        // After invalidation it should try again
        cache.invalidate("key1")
        try {
            cache["key1"]
        } catch (e: Exception) {
            val message = e.message ?: e.cause?.message
            assertEquals("loader error", message)
        }
        assertEquals(2, loaderCallCount.get(), "Loader should be called again after invalidation of failed entry")
    }
}
