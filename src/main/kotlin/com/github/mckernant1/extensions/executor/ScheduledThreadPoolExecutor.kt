package com.github.mckernant1.extensions.executor

import java.time.Duration
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.ScheduledThreadPoolExecutor
import java.util.concurrent.TimeUnit

fun ScheduledThreadPoolExecutor.scheduleAtFixedRate(
    period: Long,
    initialDelay: Long,
    timeUnit: TimeUnit,
    command: () -> Unit
): ScheduledFuture<*> = this.scheduleAtFixedRate(command, initialDelay, period, timeUnit)


fun ScheduledThreadPoolExecutor.scheduleAtFixedRate(
    period: Duration,
    command: () -> Unit
): ScheduledFuture<*> = this.scheduleAtFixedRate(command, 0, period.toMillis(), TimeUnit.MILLISECONDS)
