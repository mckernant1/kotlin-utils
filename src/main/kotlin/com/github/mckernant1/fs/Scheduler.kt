package com.github.mckernant1.fs

import java.io.Serializable
import java.time.Duration
import java.util.*
import kotlin.concurrent.scheduleAtFixedRate


fun Timer.startJobThread(
    pauseDuration: Duration,
    stopCondition: () -> Boolean = { false },
    func: () -> Unit
): TimerTask {
    return this.scheduleAtFixedRate(0, pauseDuration.toMillis()) {
        if (stopCondition()) {
            this.cancel()
        }
        func()
    }
}



fun <T : Serializable> Timer.startFileStoreRefresher(
    pauseDuration: Duration = Duration.ofHours(1),
    stopCondition: () -> Boolean = { false },
    func: () -> T
): TimerTask {
    val timedFileCache = TimedFileCache()
    val uuid = UUID.randomUUID().toString()
    return startJobThread(pauseDuration, stopCondition) {
        timedFileCache.getResult(uuid, func)
    }
}
