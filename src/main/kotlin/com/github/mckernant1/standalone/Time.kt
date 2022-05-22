package com.github.mckernant1.standalone

import com.github.mckernant1.extensions.time.elapsed
import java.time.Duration
import java.time.Instant

/**
 * @param f the function to time
 *
 * @return the duration
 */
fun measureDuration(f: () -> Unit): Duration {
    val start = Instant.now()
    f()
    return start.elapsed()
}

/**
 * @param f function to time
 *
 * @return The timed operation containing the duration and result
 */
fun <T> measureDuration(f: () -> T): TimedOperation<T> {
    val start = Instant.now()
    val result = f()
    val elapsed = start.elapsed()
    return TimedOperation(
        elapsed,
        result
    )
}

data class TimedOperation<T>(
    val duration: Duration,
    val result: T
)
