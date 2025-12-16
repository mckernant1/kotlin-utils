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
import org.apache.commons.lang3.tuple.Pair
import org.openjdk.jmh.annotations.Level
import java.util.Random
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit

private val random = Random(123412341234)

@State(Scope.Benchmark)
@Warmup(iterations = 2)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@BenchmarkMode(Mode.AverageTime)
@Measurement(iterations = 3)
class ValueSortedIndexMapBench {

    @Param("INDEXED", "HASHMAP")
    private lateinit var impl: String

    // Data shape: unique values vs many duplicates
    @Param("UNIQUE", "DUPLICATES")
    private lateinit var dataShape: String

    // Fair locking toggle for indexed impl only
    @Param("false", "true")
    private lateinit var fairLockingParam: String

    private lateinit var map: ValueSortedMap<String, Int>

    // Hot keys/values used by benchmarks
    private lateinit var hotKeyExisting: String
    private lateinit var hotKeyMissing: String
    private var hotValueExisting: Int = 0
    private var hotValueMissing: Int = -1

    private class TestHashMapImpl(
        private val comparator: Comparator<Int> = Comparator.naturalOrder()
    ) : ValueSortedMap<String, Int>, ConcurrentHashMap<String, Int>() {
        override fun minValues(): Map.Entry<Int, Set<String>>? {
            if (isEmpty()) return null
            val minVal = values.minWithOrNull(comparator) ?: return null
            val keys = entries.asSequence()
                .filter { it.value == minVal }
                .map { it.key }
                .toSet()
            return Pair.of(minVal, keys)
        }

        override fun maxValues(): Map.Entry<Int, Set<String>>? {
            if (isEmpty()) return null
            val maxVal = values.maxWithOrNull(comparator) ?: return null
            val keys = entries.asSequence()
                .filter { it.value == maxVal }
                .map { it.key }
                .toSet()
            return Pair.of(maxVal, keys)
        }
    }

    @Setup(Level.Trial)
    fun setup() {
        val fairLocking = fairLockingParam.toBooleanStrictOrNull() ?: false

        map = when (impl) {
            "INDEXED" -> ValueSortedIndexedMap(fairLocking = fairLocking)
            "HASHMAP" -> TestHashMapImpl()
            else -> throw IllegalArgumentException("Unknown impl: $impl")
        }

        val size = 100000
        val shard = 10_000 // for duplicate distribution

        // Preload according to data shape
        (1..size).shuffled(random).forEach { i ->
            val value = when (dataShape) {
                "UNIQUE" -> i
                else -> i % shard
            }
            map["$i-key"] = value
        }

        // Configure hot keys/values
        hotKeyExisting = "500-key"
        hotKeyMissing = "-1-key"
        hotValueExisting = if (dataShape == "UNIQUE") 1234 else 1234 % shard
        hotValueMissing = Int.MAX_VALUE / 2 // unlikely present
        // Ensure hot existing elements are present
        map[hotKeyExisting] = hotValueExisting
    }

    @TearDown
    fun teardown() {
        map.clear()
    }

    @Benchmark
    fun updateExistingKey() {
        map[hotKeyExisting] = hotValueExisting + 1
        map[hotKeyExisting] = hotValueExisting
    }

    @Benchmark
    fun removeAndReinsert() {
        map.remove(hotKeyExisting)
        map[hotKeyExisting] = hotValueExisting
    }

    @Benchmark
    fun getMinValue(): Map.Entry<Int, Set<String>>? = map.minValues()

    @Benchmark
    fun getMaxValue(): Map.Entry<Int, Set<String>>? = map.maxValues()
}
