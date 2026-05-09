package com.mckernant1.commons.extensions.reactor

import com.mckernant1.commons.extensions.reactor.FlowExt.associateToMap
import com.mckernant1.commons.extensions.reactor.FlowExt.collectToMap
import com.mckernant1.commons.extensions.reactor.ExpectExt.expectSingle
import com.mckernant1.commons.extensions.reactor.ExpectExt.collectListOrEmpty
import com.mckernant1.commons.extensions.reactor.AsDeferredExt.asDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@OptIn(ExperimentalCoroutinesApi::class)
class ReactorExtTest {

    @Test
    fun testCollectToMap() = runTest {
        val flow = listOf(1, 2, 3).asFlow()
        val result = flow.collectToMap(
            keySelector = { it },
            valueSelector = { it * 2 },
            mergeFunction = { v1, v2 -> v1 + v2 }
        )
        assertEquals(mapOf(1 to 2, 2 to 4, 3 to 6), result)
    }

    @Test
    fun testCollectToMapWithMerge() = runTest {
        val flow = listOf(1, 2, 1).asFlow()
        val result = flow.collectToMap(
            keySelector = { it },
            valueSelector = { it * 2 },
            mergeFunction = { v1, v2 -> v1 + v2 }
        )
        assertEquals(mapOf(1 to 4, 2 to 4), result)
    }

    @Test
    fun testAssociateToMap() = runTest {
        val flow = listOf(1, 2, 3).asFlow()
        val result = flow.associateToMap { it * 2 }
        assertEquals(mapOf(2 to 1, 4 to 2, 6 to 3), result)
    }

    @Test
    fun testAssociateToMapDuplicateKey() = runTest {
        val flow = listOf(1, 2, 1).asFlow()
        assertThrows(IllegalStateException::class.java) {
            runBlocking {
                flow.associateToMap { it }
            }
        }
    }

    @Test
    fun testExpectSingleSuccess() = runTest {
        val mono = Mono.just("test")
        val result = mono.expectSingle { RuntimeException("Fail") }
        assertEquals("test", result)
    }

    @Test
    fun testExpectSingleFailure() = runTest {
        val mono = Mono.empty<String>()
        assertThrows(RuntimeException::class.java) {
            runBlocking {
                mono.expectSingle { RuntimeException("Fail") }
            }
        }
    }

    @Test
    fun testCollectListOrEmpty() = runTest {
        val flux = Flux.just(1, 2, 3)
        val result = flux.collectListOrEmpty()
        assertEquals(listOf(1, 2, 3), result)
    }

    @Test
    fun testCollectListOrEmptyEmpty() = runTest {
        val flux = Flux.empty<Int>()
        val result = flux.collectListOrEmpty()
        assertEquals(emptyList<Int>(), result)
    }

    @Test
    fun testAsDeferred() = runTest {
        val mono = Mono.just("test")
        val deferred = with(this) {
            mono.asDeferred()
        }
        assertEquals("test", deferred.await())
    }

    @Test
    fun testAsDeferredWithThrowable() = runTest {
        val mono = Mono.just("test")
        val deferred = with(this) {
            mono.asDeferred { RuntimeException("Fail") }
        }
        assertEquals("test", deferred.await())
    }

    @Test
    fun testAsDeferredWithThrowableFailure(): Unit = runBlocking {
        val mono = Mono.empty<String>()
        val deferred = async(SupervisorJob()) {
            mono.expectSingle { RuntimeException("Fail") }
        }
        try {
            deferred.await()
            fail("Expected RuntimeException")
        } catch (e: RuntimeException) {
            assertEquals("Fail", e.message)
        } catch (e: Exception) {
            val cause = e.cause
            if (cause is RuntimeException) {
                assertEquals("Fail", cause.message)
            } else {
                throw e
            }
        }
    }

    private fun <T> CoroutineScope.asDeferred(block: suspend () -> T) = async { block() }
}
