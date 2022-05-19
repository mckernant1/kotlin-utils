package com.github.mckernant1.extensions.collections

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking


fun <T, R> Iterable<T>.cartesianProduct(other: Iterable<R>): List<Pair<T, R>> =
    this.flatMap { outer ->
        other.map { inner ->
            Pair(outer, inner)
        }
    }


fun <T> Iterable<T>?.equals(other: Iterable<T>?, preserveOrder: Boolean = true): Boolean =
    when {
        other == null || this == null -> this == null && other == null
        preserveOrder -> this == other
        else -> this.fold(true) { acc: Boolean, t: T ->
            acc && other.any { it == t }
        }
    }

fun <T, R> Iterable<T>.mapParallel(transform: suspend (T) -> R): List<R> = runBlocking {
    return@runBlocking map { async { transform(it) } }.awaitAll()
}

fun <T> Iterable<T>.intersect(other: Iterable<T>): List<T> {
    return this.filter { other.contains(it) }
}

/**
 * Returns all the elements that are in this, but not in other
 */
fun <T> Iterable<T>.except(other: Iterable<T>): List<T> {
    return this.filter { !other.contains(it) }
}

fun <T, K> Iterable<T>.intersectBy(other: Iterable<T>, selector: (T) -> K): List<T> {
    val otherSet = other.map { selector(it) }.toHashSet()
    return this.filter { otherSet.contains(selector(it)) }
}

fun <T, K> Iterable<T>.exceptBy(other: Iterable<T>, selector: (T) -> K): List<T> {
    val otherSet = other.map { selector(it) }.toHashSet()
    return this.filter { !otherSet.contains(selector(it)) }
}
