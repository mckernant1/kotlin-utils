package com.github.mckernant1.utils.caching

import java.time.Duration
import java.util.concurrent.ScheduledThreadPoolExecutor
import java.util.concurrent.TimeUnit

class PeriodicUpdatingInMemoryCache<T>(
    period: Duration,
    threadPool: ScheduledThreadPoolExecutor = ScheduledThreadPoolExecutor(1),
    val updateFunc: () -> T
) {

    @set:Synchronized
    @get:Synchronized
    private var item: T? = null

    init {
        threadPool.scheduleAtFixedRate(
            {
            item = updateFunc()
            },
            0,
            period.toMillis(),
            TimeUnit.MILLISECONDS
        )
    }

    fun getValue(): T {
        return if (item == null) {
            updateFunc()
        } else {
            item!!
        }
    }

}
