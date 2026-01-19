package com.mckernant1.commons.caching

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock


class SuspendCache<K, V>(
    private val scope: CoroutineScope,
    private val loader: suspend (K) -> V
) {

    private val mutex = Mutex()
    private val cache = mutableMapOf<K, Deferred<V>>()


    suspend operator fun get(key: K): V {
        val deferred = mutex.withLock {
            cache[key] ?: scope.async(start = CoroutineStart.LAZY) {
                loader(key)
            }.also { cache[key] = it }
        }

        return deferred.await()
    }

    suspend fun invalidate(key: K) {
        mutex.withLock {
            cache.remove(key)?.cancel()
        }
    }

}
