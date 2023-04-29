package com.mckernant1.commons.standalone

import java.time.Duration

suspend fun delay(duration: Duration) {
    kotlinx.coroutines.delay(duration.toMillis())
}
