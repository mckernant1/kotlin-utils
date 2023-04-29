package com.mckernant1.commons.extensions.boolean

fun Boolean?.falseIfNull(): Boolean = this ?: false
