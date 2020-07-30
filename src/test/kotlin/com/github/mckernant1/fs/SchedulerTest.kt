package com.github.mckernant1.fs

import org.junit.jupiter.api.Test
import java.time.Duration

internal class SchedulerTest {

    @Test
    fun startJobThread() {
        val x = mutableListOf<String>()
        var c = 0
        startJobThread(Duration.ofSeconds(5), { c == 5 }) {
            x.add("Hello!")
            ++c
        }.join()
    }
}
