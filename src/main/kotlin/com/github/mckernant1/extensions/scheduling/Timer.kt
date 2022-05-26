package com.github.mckernant1.extensions.scheduling

import kotlin.concurrent.scheduleAtFixedRate
import java.time.Duration
import java.util.Timer
import java.util.TimerTask

inline fun Timer.startJobThread(
    pauseDuration: Duration,
    crossinline stopCondition: () -> Boolean = { false },
    crossinline func: () -> Unit
): TimerTask {
    return this.scheduleAtFixedRate(0, pauseDuration.toMillis()) {
        if (stopCondition()) {
            this.cancel()
        }
        func()
    }
}
