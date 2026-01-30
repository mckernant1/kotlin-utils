package com.mckernant1.commons.extensions.coroutines

import com.mckernant1.commons.extensions.time.Instants.elapsed
import com.mckernant1.commons.standalone.delay
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.Instant

object Schedule {

    /**
     * Schedule a coroutine to run after a specified delay.
     */
    fun CoroutineScope.schedule(
        delay: Duration,
        block: suspend () -> Unit
    ): Job = launch {
        delay(delay)
        block()
    }

    /**
     * Schedule a coroutine to run at a fixed rate, with an initial delay and period.
     */
    fun CoroutineScope.scheduleAtFixedRate(
        initialDelay: Duration,
        period: Duration,
        block: suspend () -> Unit
    ): Job = launch {
        delay(initialDelay)
        while (isActive) {
            val start = Instant.now()
            block()
            val remaining = period - Duration.between(start, Instant.now())
            if (remaining.isPositive) {
                delay(remaining)
            }
        }
    }

}
