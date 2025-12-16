package com.mckernant1.commons.maps

import org.jetbrains.lincheck.datastructures.IntGen
import org.jetbrains.lincheck.datastructures.Operation
import org.jetbrains.lincheck.datastructures.Param
import org.jetbrains.lincheck.datastructures.StressOptions
import org.jetbrains.lincheck.datastructures.StringGen
import org.junit.jupiter.api.Test

@Param(
    name = "key",
    gen = StringGen::class
)
@Param(
    name = "value",
    gen = IntGen::class
)
class ValueIndexedDoubleMapConcurrencyTest {

    private val map = ValueIndexedDoubleMap<String, Int>()

    @Operation
    fun put(@Param(name = "key") key: String, @Param(name = "value") value: Int) =
        map.put(key, value)

    @Operation
    fun get(@Param(name = "key") key: String): Int? = map[key]

    @Operation
    fun remove(@Param(name = "key") key: String): Int? = map.remove(key)

    @Operation
    fun clear() = map.clear()

    @Operation
    fun getKeysForValue(@Param(name = "value") value: Int): Set<String>? =
        map.getKeysForValue(value)

    @Test
    fun stressTest() = StressOptions()
        .actorsBefore(1)
        .actorsAfter(1)
        .check(this::class)
}
