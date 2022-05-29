package com.github.mckernant1.extensions.regex

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test


internal class RegexTest {

    @Test
    fun testContains() {

        val x = when ("Hello Tom19") {
            in Regex("\\w+ \\w+\\d+") -> true
            else -> false
        }

        assertTrue(x)

    }

}
