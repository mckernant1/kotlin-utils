package com.mckernant1.extensions.boolean

fun Boolean?.falseIfNull(): Boolean = this ?: false
