package com.mckernant1.commons.extensions.time

import java.time.LocalDate
import java.time.ZonedDateTime
import java.time.chrono.ChronoLocalDate
import java.time.chrono.ChronoZonedDateTime

object ChronoLocalDates {
    fun ChronoLocalDate.isBeforeNow(): Boolean = this.isBefore(LocalDate.now())

    fun ChronoLocalDate.isAfterNow(): Boolean = this.isAfter(LocalDate.now())
}

object ChronoZonedDateTimes {
    fun <D : ChronoLocalDate> ChronoZonedDateTime<D>.isBeforeNow(): Boolean = this.isBefore(ZonedDateTime.now())

    fun <D : ChronoLocalDate> ChronoZonedDateTime<D>.isAfterNow(): Boolean = this.isAfter(ZonedDateTime.now())
}
