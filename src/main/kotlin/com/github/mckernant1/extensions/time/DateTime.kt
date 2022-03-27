package com.github.mckernant1.extensions.time

import java.time.LocalDate
import java.time.ZonedDateTime

fun LocalDate.isBeforeNow(): Boolean = this.isBefore(LocalDate.now())

fun LocalDate.isAfterNow(): Boolean = this.isAfter(LocalDate.now())


fun ZonedDateTime.isBeforeNow(): Boolean = this.isBefore(ZonedDateTime.now())

fun ZonedDateTime.isAfterNow(): Boolean = this.isAfter(ZonedDateTime.now())
