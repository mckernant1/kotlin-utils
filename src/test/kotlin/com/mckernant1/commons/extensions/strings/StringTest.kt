package com.mckernant1.commons.extensions.strings

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class StringTest {

    @Test
    fun capitalize() {
        assertEquals("hello".capitalize(), "Hello")
    }
}
