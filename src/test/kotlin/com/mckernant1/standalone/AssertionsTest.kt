package com.mckernant1.standalone

import com.mckernant1.assertions.AssertionException
import com.mckernant1.assertions.Assertions
import com.mckernant1.extensions.boolean.falseIfNull
import com.mckernant1.assertions.Assertions.assertEnvironmentVariablesExist
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class AssertionsTest {

    @Test
    fun validateEnvironmentVariablesTest() {
        val x = assertThrows<com.mckernant1.assertions.AssertionException> {
            assertEnvironmentVariablesExist(
                "BOT_TOKEN",
                "API_KEY"
            )
        }
        assertTrue(x.message?.contains("BOT_TOKEN").falseIfNull())
        assertTrue(x.message?.contains("API_KEY").falseIfNull())

        assertEnvironmentVariablesExist("PWD")
    }

    @Test
    fun testAssertTrue() {
        assertThrows<com.mckernant1.assertions.AssertionException> {
            com.mckernant1.assertions.Assertions.assertTrue(false)
        }
        com.mckernant1.assertions.Assertions.assertTrue(true)
    }

    @Test
    fun testAssertFalse() {
        assertThrows<com.mckernant1.assertions.AssertionException> {
            com.mckernant1.assertions.Assertions.assertFalse(true)
        }
        com.mckernant1.assertions.Assertions.assertFalse(false)
    }

    @Test
    fun testAssertEquals() {
        assertThrows<com.mckernant1.assertions.AssertionException> {
            com.mckernant1.assertions.Assertions.assertEquals(1, 2)
        }
        com.mckernant1.assertions.Assertions.assertEquals(1, 1)
    }

    @Test
    fun testAssertNotEquals() {
        assertThrows<com.mckernant1.assertions.AssertionException> {
            com.mckernant1.assertions.Assertions.assertNotEquals(1, 1)
        }
        com.mckernant1.assertions.Assertions.assertNotEquals(1, 2)
    }

    @Test
    fun testAssertThrows() {

        com.mckernant1.assertions.Assertions.assertThrows<IllegalStateException> {
            throw IllegalStateException()
        }

        assertThrows<com.mckernant1.assertions.AssertionException> {
            com.mckernant1.assertions.Assertions.assertThrows<IllegalStateException> {
                throw IllegalArgumentException()
            }
        }

        assertThrows<com.mckernant1.assertions.AssertionException> {
            com.mckernant1.assertions.Assertions.assertThrows<IllegalStateException> {
                1 + 1
            }
        }

    }


}
