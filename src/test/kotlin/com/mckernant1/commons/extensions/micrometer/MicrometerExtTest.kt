package com.mckernant1.commons.extensions.micrometer

import com.mckernant1.commons.extensions.micrometer.TracerExt.withNewSpan
import com.mckernant1.commons.extensions.micrometer.ObservationExt.observe
import io.micrometer.observation.ObservationRegistry
import io.micrometer.tracing.Tracer
import io.micrometer.tracing.Span
import io.micrometer.observation.Observation
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when` as wheneverStatic
import org.mockito.Mockito.mockStatic
import org.mockito.Mockito.withSettings
import org.mockito.kotlin.*

class MicrometerExtTest {

    @Test
    fun testWithNewSpan() = runTest {
        val span = mock<Span>()
        val tracer = mock<Tracer> {
            on { nextSpan() } doReturn span
        }
        whenever(span.name(any())).thenReturn(span)
        
        val result = tracer.withNewSpan("test-span", "tag1" to "value1") {
            assertEquals(span, it)
            "result"
        }

        assertEquals("result", result)
        verify(span).name("test-span")
        verify(span).tag("tag1", "value1")
        verify(span).end()
    }

    @Test
    fun testObserve() = runTest {
        val registry = mock<ObservationRegistry>()
        val observation = mock<Observation>()
        val scope = org.mockito.Mockito.mock(Observation.Scope::class.java)

        mockStatic(Observation::class.java).use { observationStatic ->
            observationStatic.`when`<Observation> { 
                Observation.createNotStarted(any<String>(), eq(registry)) 
            }.thenReturn(observation)
            
            whenever(observation.lowCardinalityKeyValues(any())).thenReturn(observation)
            whenever(observation.highCardinalityKeyValues(any())).thenReturn(observation)
            whenever(observation.start()).thenReturn(observation)
            whenever(observation.openScope()).thenReturn(scope)

            val result = registry.observe("test-observation") {
                "result"
            }

            assertEquals("result", result)
            verify(observation).start()
            verify(observation).stop()
        }
    }
}
