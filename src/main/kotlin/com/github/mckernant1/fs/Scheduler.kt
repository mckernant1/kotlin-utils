package com.github.mckernant1.fs

import java.io.Serializable
import java.time.Duration
import java.util.*
import kotlin.concurrent.thread


fun startJobThread(
    pauseDuration: Duration,
    stopCondition: () -> Boolean = { false },
    func: () -> Unit
): Thread {
    return thread {
        while (!stopCondition()) {
            func()
            Thread.sleep(pauseDuration.toMillis())
        }
    }
}

val fileHandler = FileHandler()

fun <T : Serializable> startFileStoreSchedule(
    pauseDuration: Duration = Duration.ofHours(1),
    stopCondition: () -> Boolean = { false },
    func: () -> T) {
    val uuid = UUID.randomUUID().toString()
    startJobThread(pauseDuration, stopCondition) {
        fileHandler.getResult(uuid, func)
    }
}
