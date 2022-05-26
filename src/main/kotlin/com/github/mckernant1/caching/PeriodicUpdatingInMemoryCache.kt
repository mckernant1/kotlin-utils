package com.github.mckernant1.caching

import com.github.mckernant1.extensions.executor.scheduleAtFixedRate
import java.time.Duration
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.ScheduledThreadPoolExecutor

class PeriodicUpdatingInMemoryCache<T>(
    period: Duration,
    threadPool: ScheduledThreadPoolExecutor = ScheduledThreadPoolExecutor(1),
    private val updateFunc: () -> T
) {

    @get:Synchronized
    @set:Synchronized
    private var item: T? = null

    private val future: ScheduledFuture<*> = threadPool.scheduleAtFixedRate(period) {
        item = updateFunc()
    }

    /**
     * @return the item
     */
    fun getValue(): T = item ?: updateFunc()

    /**
     * See **[ScheduledFuture.cancel]**
     */
    fun cancel(mayInterruptIfRunning: Boolean = false): Boolean = future.cancel(mayInterruptIfRunning)
}
