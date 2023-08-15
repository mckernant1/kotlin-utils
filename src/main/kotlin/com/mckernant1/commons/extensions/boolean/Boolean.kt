package com.mckernant1.commons.extensions.boolean

fun Boolean?.falseIfNull(): Boolean = this ?: false

fun Boolean?.trueIfNull(): Boolean = this ?: true
