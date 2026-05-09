package com.mckernant1.commons.extensions.micrometer

import kotlinx.coroutines.withContext
import io.micrometer.common.KeyValue
import io.micrometer.common.KeyValues
import io.micrometer.core.instrument.kotlin.asContextElement
import io.micrometer.observation.Observation
import io.micrometer.observation.ObservationRegistry
import io.micrometer.tracing.Span
import io.micrometer.tracing.Tracer


object TracerExt {
    /**
     * Executes the [block] within a new span.
     * @param name the name of the span
     * @param tags tags to be added to the span
     * @param block the code to execute within the span
     * @return the result of the [block]
     */
    suspend fun <T> Tracer.withNewSpan(
        name: String,
        vararg tags: Pair<String, String>,
        block: suspend (Span) -> T
    ): T {
        val span = nextSpan()
            .name(name)
        tags.forEach { (key, value) -> span.tag(key, value) }
        return try {
            block(span)
        } finally {
            span.end()
        }
    }
}

object ObservationExt {
    /**
     * Executes the [block] within an observation.
     * @param name the name of the observation
     * @param lowCardinalityValues low cardinality key-values for the observation
     * @param highCardinalityValues high cardinality key-values for the observation
     * @param block the code to execute
     * @return the result of the [block]
     */
    suspend fun <T> ObservationRegistry.observe(
        name: String,
        lowCardinalityValues: Iterable<KeyValue> = setOf(),
        highCardinalityValues: Iterable<KeyValue> = setOf(),
        block: suspend () -> T
    ): T {
        val observation = Observation.createNotStarted(name, this)
            .lowCardinalityKeyValues(KeyValues.of(lowCardinalityValues))
            .highCardinalityKeyValues(KeyValues.of(highCardinalityValues))
            .start()

        return withContext(observation.openScope().use { this.asContextElement() }) {
            try {
                block()
            } catch (e: Throwable) {
                observation.error(e)
                throw e
            } finally {
                observation.stop()
            }
        }
    }
}
