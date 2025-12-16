package com.mckernant1.commons.maps

import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.BenchmarkMode
import kotlinx.benchmark.Measurement
import kotlinx.benchmark.Mode
import kotlinx.benchmark.OutputTimeUnit
import kotlinx.benchmark.Param
import kotlinx.benchmark.Scope
import kotlinx.benchmark.Setup
import kotlinx.benchmark.State
import kotlinx.benchmark.TearDown
import kotlinx.benchmark.Warmup
import org.openjdk.jmh.annotations.Level
import java.util.Random
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit

private val rndIdx = Random(42424242)

@State(Scope.Benchmark)
@Warmup(iterations = 2)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@BenchmarkMode(Mode.AverageTime)
@Measurement(iterations = 3)
class ValueIndexedDoubleMapBench {

    @Param("INDEXED", "HASHMAP")
    private lateinit var impl: String

    private lateinit var map: ValueIndexedMap<String, Int>

    // Hot keys/values used by benchmarks
    private lateinit var hotKeyExisting: String
    private lateinit var hotKeyMissing: String
    private var hotValueExisting: Int = 0
    private var hotValueMissing: Int = -1


    private class TestHashMapImpl :
        ValueIndexedMap<String, Int>,
        ConcurrentHashMap<String, Int>() {
        override fun getKeysForValue(value: Int): Set<String>? {
            val keys = entries.asSequence()
                .filter { it.value == value }
                .map { it.key }
                .toSet()
            return keys.ifEmpty { null }
        }
    }

    @Setup(Level.Trial)
    fun setup() {
        map = when (impl) {
            "INDEXED" -> ValueIndexedDoubleMap()
            "HASHMAP" -> TestHashMapImpl()
            else -> throw IllegalArgumentException("Unknown impl: $impl")
        }

        // Preload with many values
        (1..100_000).shuffled(rndIdx).forEach {
            map["$it-key"] = it % 10_000 // many duplicate values to exercise index
        }

        hotKeyExisting = "500-key"
        hotKeyMissing = "-1-key"
        hotValueExisting = 1234
        hotValueMissing = 99_999
        // Ensure hot values/keys exist as expected
        map[hotKeyExisting] = hotValueExisting
    }

    @TearDown
    fun tearDown() {
        map.clear()
    }

    @Benchmark
    fun getKeysForValueExisting(): Set<String>? = map.getKeysForValue(hotValueExisting)

    @Benchmark
    fun removeAndReinsert() {
        map.remove(hotKeyExisting)
        map[hotKeyExisting] = hotValueExisting
    }
}
