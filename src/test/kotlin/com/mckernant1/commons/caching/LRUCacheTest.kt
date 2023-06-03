package com.mckernant1.commons.caching

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

/**
 * Ty ChatGpt... Still cant write tests for access order though...
 */
class LRUCacheTest {

    @Test
    fun testCachesize() {
        val cache: CacheMap<String, Int> = LRUCache(3)
        assertEquals(0, cache.size)

        cache["key1"] = 1
        assertEquals(1, cache.size)

        cache["key2"] = 2
        assertEquals(2, cache.size)

        cache["key3"] = 3
        assertEquals(3, cache.size)

        cache["key4"] = 4
        assertEquals(3, cache.size) // The cache size should be limited to 3
    }

    @Test
    fun testCacheGet() {
        val cache: CacheMap<String, Int> = LRUCache(3)
        cache["key1"] = 1
        cache["key2"] = 2
        cache["key3"] = 3

        assertEquals(1, cache["key1"])
        assertEquals(2, cache["key2"])
        assertEquals(3, cache["key3"])
        assertNull(cache["key4"]) // Key not present in cache
    }

    @Test
    fun testCacheLRU() {
        val cache: CacheMap<String, Int> = LRUCache(3)
        cache["key1"] = 1
        cache["key2"] = 2
        cache["key3"] = 3

        cache["key4"] = 4 // Causes eviction of key1 as the cache size is limited to 3

        assertNull(cache["key1"]) // key1 should be evicted from the cache
        assertEquals(2, cache["key2"])
        assertEquals(3, cache["key3"])
        assertEquals(4, cache["key4"])
    }

    @Test
    fun testAccessOrder() {
        val cache: CacheMap<String, Int> = LRUCache(3)
        cache["key1"] = 1
        cache["key2"] = 2

        assertEquals(1, cache["key1"])

        cache["key3"] = 3
        cache["key4"] = 4

        // key2 should be evicted from the cache as it was the least recently accessed
        assertNull(cache["key2"])

        assertEquals(1, cache["key1"])
        assertEquals(3, cache["key3"])
        assertEquals(4, cache["key4"])
    }
}
