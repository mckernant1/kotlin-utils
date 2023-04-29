package com.mckernant1.commons.lazy

import java.util.LinkedList
import kotlin.reflect.KProperty

/**
 * Taken from
 * https://stackoverflow.com/questions/35752575/kotlin-lazy-properties-and-values-reset-a-resettable-lazy-delegate
 *
 */

class ResettableLazyManager {
    // we synchronize to make sure the timing of a reset() call and new inits do not collide
    private val managedDelegates = LinkedList<Resettable>()

    fun register(managed: Resettable) {
        synchronized(managedDelegates) {
            managedDelegates.add(managed)
        }
    }

    fun reset() {
        synchronized(managedDelegates) {
            managedDelegates.forEach { it.reset() }
            managedDelegates.clear()
        }
    }
}

interface Resettable {
    fun reset()
}

class ResettableLazy<T>(
    private val manager: ResettableLazyManager,
    private val init: () -> T
) : Resettable {
    @Volatile
    private var lazyHolder = makeInitBlock()

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return lazyHolder.value
    }

    override fun reset() {
        lazyHolder = makeInitBlock()
    }

    private fun makeInitBlock(): Lazy<T> {
        return lazy {
            manager.register(this)
            init()
        }
    }
}

fun <T> resettableLazy(manager: ResettableLazyManager, init: () -> T): ResettableLazy<T> {
    return ResettableLazy(manager, init)
}

fun resettableManager(): ResettableLazyManager = ResettableLazyManager()
