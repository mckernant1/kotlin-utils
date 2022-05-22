package com.github.mckernant1.standalone

import com.github.mckernant1.extensions.boolean.falseIfNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class AssertionsTest {

    @Test
    fun validateEnvironmentVariablesTest() {
        val x = assertThrows<IllegalStateException> {
            assertEnvironmentVariablesExist(
                "BOT_TOKEN",
                "API_KEY"
            )
        }

        assertTrue(x.message?.contains("BOT_TOKEN").falseIfNull())
        assertTrue(x.message?.contains("API_KEY").falseIfNull())

    }

}
