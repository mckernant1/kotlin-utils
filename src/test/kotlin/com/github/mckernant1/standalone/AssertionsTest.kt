package com.github.mckernant1.standalone

import com.github.mckernant1.assertions.AssertionException
import com.github.mckernant1.assertions.Assertions
import com.github.mckernant1.extensions.boolean.falseIfNull
import com.github.mckernant1.assertions.Assertions.assertEnvironmentVariablesExist
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class AssertionsTest {

    @Test
    fun validateEnvironmentVariablesTest() {
        val x = assertThrows<AssertionException> {
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
        assertThrows<AssertionException> {
            Assertions.assertTrue(false)
        }
        Assertions.assertTrue(true)
    }

    @Test
    fun testAssertFalse() {
        assertThrows<AssertionException> {
            Assertions.assertFalse(true)
        }
        Assertions.assertFalse(false)
    }

    @Test
    fun testAssertEquals() {
        assertThrows<AssertionException> {
            Assertions.assertEquals(1, 2)
        }
        Assertions.assertEquals(1, 1)
    }

    @Test
    fun testAssertNotEquals() {
        assertThrows<AssertionException> {
            Assertions.assertNotEquals(1, 1)
        }
        Assertions.assertNotEquals(1, 2)
    }

    @Test
    fun testAssertThrows() {

        Assertions.assertThrows<IllegalStateException> {
            throw IllegalStateException()
        }

        assertThrows<AssertionException> {
            Assertions.assertThrows<IllegalStateException> {
                throw IllegalArgumentException()
            }
        }

        assertThrows<AssertionException> {
            Assertions.assertThrows<IllegalStateException> {
                1 + 1
            }
        }

    }


}
