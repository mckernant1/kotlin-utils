package com.github.mckernant1.fs

import org.junit.jupiter.api.Test
import java.time.Duration
import java.util.*

internal class SchedulerTest {


    private val timer = Timer()

    @Test
    fun startJobThread() {
        val x = mutableListOf<String>()
        var c = 0
        val t = timer.startJobThread(Duration.ofSeconds(5), { c == 5 }) {
            x.add("Hello!")
            ++c
        }
    }


    @Test
    fun startFStoreSchedule() {
        var c = 0
        val t = timer.startFileStoreRefresher(Duration.ofSeconds(1), { c == 5 }) {
            ++c
        }
    }
}
