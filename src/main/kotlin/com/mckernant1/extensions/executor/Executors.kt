package com.mckernant1.extensions.executor

import java.time.Duration
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit

object Executors {

    fun ScheduledExecutorService.scheduleAtFixedRate(
        period: Long,
        initialDelay: Long,
        timeUnit: TimeUnit,
        command: () -> Unit
    ): ScheduledFuture<*> = this.scheduleAtFixedRate(command, initialDelay, period, timeUnit)


    fun ScheduledExecutorService.scheduleAtFixedRate(
        period: Duration,
        command: () -> Unit
    ): ScheduledFuture<*> = this.scheduleAtFixedRate(command, 0, period.toMillis(), TimeUnit.MILLISECONDS)


    fun ScheduledExecutorService.scheduleWithFixedDelay(
        period: Long,
        initialDelay: Long,
        timeUnit: TimeUnit,
        command: () -> Unit
    ): ScheduledFuture<*> = this.scheduleWithFixedDelay(command, initialDelay, period, timeUnit)

    fun ScheduledExecutorService.scheduleWithFixedDelay(
        delay: Duration,
        command: () -> Unit
    ): ScheduledFuture<*> = this.scheduleWithFixedDelay(command, 0, delay.toMillis(), TimeUnit.MILLISECONDS)
}

