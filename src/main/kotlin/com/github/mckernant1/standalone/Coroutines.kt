package com.github.mckernant1.standalone

import java.time.Duration

suspend fun delay(duration: Duration) {
    kotlinx.coroutines.delay(duration.toMillis())
}
