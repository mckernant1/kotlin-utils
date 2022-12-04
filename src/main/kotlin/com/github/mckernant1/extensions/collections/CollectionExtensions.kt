package com.github.mckernant1.extensions.collections





object SetTheory {
    /**
     * Returns the cartesianProduct of 2 lists
     */
    fun <T, R> Iterable<T>.cartesianProduct(other: Iterable<R>): List<Pair<T, R>> = this.flatMap { outer ->
        other.map { inner -> Pair(outer, inner) }
    }

    /**
     * Returns all the elements in this and other
     */
    fun <T> Iterable<T>.intersect(other: Iterable<T>): List<T> {
        val otherSet = other.toHashSet()
        return this.filter { otherSet.contains(it) }
    }

    /**
     * Returns all the elements that are in this, but not in other
     */
    fun <T> Iterable<T>.except(other: Iterable<T>): List<T> {
        val otherSet = other.toHashSet()
        return this.filter { !otherSet.contains(it) }
    }

    inline fun <T, K> Iterable<T>.intersectBy(other: Iterable<T>, selector: (T) -> K): List<T> {
        val otherSet = other.map { selector(it) }.toHashSet()
        return this.filter { otherSet.contains(selector(it)) }
    }

    inline fun <T, K> Iterable<T>.exceptBy(other: Iterable<T>, selector: (T) -> K): List<T> {
        val otherSet = other.map { selector(it) }.toHashSet()
        return this.filter { !otherSet.contains(selector(it)) }
    }

}
