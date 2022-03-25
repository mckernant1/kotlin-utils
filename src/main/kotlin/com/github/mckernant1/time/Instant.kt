package com.github.mckernant1.time

import java.time.Duration
import java.time.Instant

fun Instant.elapsed(): Duration = Duration.between(this, Instant.now())
