package com.github.mckernant1.extensions.scheduling

import kotlin.concurrent.scheduleAtFixedRate
import java.time.Duration
import java.util.Timer
import java.util.TimerTask

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
