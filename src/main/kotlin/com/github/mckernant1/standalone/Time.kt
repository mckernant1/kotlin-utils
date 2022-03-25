package com.github.mckernant1.standalone

import com.github.mckernant1.extensions.time.elapsed
import java.time.Duration
import java.time.Instant

fun measureDuration(f: () -> Unit): Duration {
    val start = Instant.now()
    f()
    return start.elapsed()
}
