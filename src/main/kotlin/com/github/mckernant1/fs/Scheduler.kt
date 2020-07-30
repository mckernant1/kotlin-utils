package com.github.mckernant1.fs

import java.time.Duration
import kotlin.concurrent.thread


fun startJobThread(
    duration: Duration,
    stopCondition: () -> Boolean = { true },
    func: () -> Unit
): Thread {
    return thread {
        while (!stopCondition()) {
            func()
            Thread.sleep(duration.toMillis())
        }
    }
}
