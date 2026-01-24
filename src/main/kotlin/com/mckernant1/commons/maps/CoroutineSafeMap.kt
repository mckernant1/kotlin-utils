package com.mckernant1.commons.maps

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class CoroutineSafeMap<K, V>(
    private val backingMap: MutableMap<K, V> = mutableMapOf()
) {

    private val mutex = Mutex()

    suspend operator fun get(key: K): V? = mutex.withLock { backingMap[key] }

    suspend operator fun set(key: K, value: V) = mutex.withLock {
        backingMap[key] = value
    }

    suspend fun clear() = mutex.withLock { backingMap.clear() }


    suspend fun <R> withLock(block: suspend MutableMap<K, V>.() -> R) = mutex.withLock {
        backingMap.block()
    }

}
