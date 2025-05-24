package com.mckernant1.commons.maps

import org.jetbrains.kotlinx.lincheck.annotations.Operation
import org.jetbrains.kotlinx.lincheck.annotations.Param
import org.jetbrains.kotlinx.lincheck.check
import org.jetbrains.kotlinx.lincheck.paramgen.StringGen
import org.jetbrains.kotlinx.lincheck.strategy.stress.StressOptions
import org.junit.jupiter.api.Test

@Param(name = "key", gen = StringGen::class)
class ValueSortedIndexedMapConcurrencyTest {

    private val map = ValueSortedIndexedMap<String, Int>()

    @Operation
    fun put(@Param(name = "key") key: String, value: Int) = map.put(key, value)

    @Operation
    fun get(@Param(name = "key") key: String): Int? = map[key]

    @Operation
    fun remove(@Param(name = "key") key: String): Int? = map.remove(key)

    @Operation
    fun clear() = map.clear()

    @Operation
    fun minValue(): Map.Entry<String, Int>? = map.minValue()

    @Operation
    fun maxValue(): Map.Entry<String, Int>? = map.maxValue()

    @Test
    fun stressTest() = StressOptions().check(this::class)
}
