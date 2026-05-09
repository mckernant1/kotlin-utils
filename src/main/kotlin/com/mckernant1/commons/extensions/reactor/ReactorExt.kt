package com.mckernant1.commons.extensions.reactor

import com.mckernant1.commons.extensions.reactor.ExpectExt.expectSingle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactor.awaitSingleOrNull
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import kotlin.collections.orEmpty

object FlowExt {

    /**
     * Collects the flow into a map using the provided selectors and merge function.
     * @param keySelector function to extract the key from each element
     * @param valueSelector function to extract the value from each element
     * @param mergeFunction function to merge values when a duplicate key is encountered
     * @param mapSupplier function to provide the initial map implementation
     * @return a map containing the collected elements
     */
    suspend fun <T, K, V : Any> Flow<T>.collectToMap(
        keySelector: (T) -> K,
        valueSelector: (T) -> V,
        mergeFunction: (V, V) -> V,
        mapSupplier: () -> MutableMap<K, V> = { LinkedHashMap() }
    ): Map<K, V> {

        val map = mapSupplier()

        collect { elem ->
            map.merge(
                keySelector(elem),
                valueSelector(elem),
                mergeFunction
            )
        }

        return map
    }

    /**
     * Associates elements of the flow into a map where keys are provided by [keySelector].
     * Throws [IllegalStateException] if duplicate keys are encountered.
     * @param keySelector function to extract the key from each element
     * @return a map containing the elements indexed by keys
     */
    suspend fun <T : Any, K> Flow<T>.associateToMap(
        keySelector: (T) -> K,
    ): Map<K, T> {
        return collectToMap(
            keySelector,
            { it },
            { _, _ -> throw IllegalStateException("Duplicate key not allowed") }
        )
    }
}

object ExpectExt {

    /**
     * Awaits the single element of the Mono or throws the provided exception if empty.
     * @param throwable function providing the exception to throw if the Mono is empty
     * @return the single element of the Mono
     */
    suspend inline fun <T : Any> Mono<T>.expectSingle(
        throwable: () -> Throwable
    ): T = this.awaitSingleOrNull()
        ?: throw throwable()

    /**
     * Collects all elements of the Flux into a list, or returns an empty list if the Flux is empty.
     */
    suspend fun <T : Any> Flux<T>.collectListOrEmpty(): List<T> =
        this.collectList()
            .awaitSingleOrNull()
            .orEmpty()
}

/**
 * Eagerly subscribe to the Mono and return a deferred value.
 *
 *
 * Do not use with GlobalScope.
 */
object AsDeferredExt {

    /**
     * Eagerly subscribes to the Mono and returns a [Deferred] value.
     * If the Mono is empty, the deferred will yield null.
     */
    context(scope: CoroutineScope)
    fun <T : Any> Mono<T>.asDeferred(): Deferred<T?> = scope.async {
        this@asDeferred.awaitSingleOrNull()
    }

    /**
     * Eagerly subscribes to the Mono and returns a [Deferred] value.
     * If the Mono is empty, the deferred will yield the exception provided by [throwable].
     */
    context(scope: CoroutineScope)
    fun <T : Any> Mono<T>.asDeferred(
        throwable: () -> Throwable
    ): Deferred<T> = scope.async {
        this@asDeferred.expectSingle(throwable)
    }

}
