package com.mckernant1.commons.assertions

/**
 * Assertions for use in source code. Not in tests
 */
object Assertions {
    @Throws(com.mckernant1.commons.assertions.AssertionException::class)
    fun assertEnvironmentVariablesExist(vararg variables: String) {
        val unSet = variables.asSequence()
            .map { it to (System.getenv(it) ?: null) }
            .filter { (_, value) -> value == null }
            .map { (key, _) -> key }
            .toSet()

        if (unSet.isNotEmpty()) {
            throw com.mckernant1.commons.assertions.AssertionException("Environment variables '$unSet' are not defined")
        }
    }

    /**
     * If the given expression was false, throw an exception
     */
    @Throws(com.mckernant1.commons.assertions.AssertionException::class)
    fun assertTrue(expression: Boolean) {
        if (!expression) {
            throw com.mckernant1.commons.assertions.AssertionException("Expression was false")
        }
    }

    /**
     * If the given expression was true, throw an exception
     */
    @Throws(com.mckernant1.commons.assertions.AssertionException::class)
    fun assertFalse(expression: Boolean) {
        if (expression) {
            throw com.mckernant1.commons.assertions.AssertionException("Expression was true")
        }
    }

    /**
     * If the given two values are not equal, throw an exception
     */
    @Throws(com.mckernant1.commons.assertions.AssertionException::class)
    fun <T> assertEquals(left: T, right: T) {
        if (left != right) {
            throw com.mckernant1.commons.assertions.AssertionException("left: '$left' did not match right: '$right'")
        }
    }

    /**
     * If the given two values are equal, throw an exception
     */
    @Throws(com.mckernant1.commons.assertions.AssertionException::class)
    fun <T> assertNotEquals(left: T, right: T) {
        if (left == right) {
            throw com.mckernant1.commons.assertions.AssertionException("left: '$left' matched right: '$right'")
        }
    }

    /**
     * If the function throws an exception is not thrown or does not match the input type
     */
    @Throws(com.mckernant1.commons.assertions.AssertionException::class)
    inline fun <reified T> assertThrows(f: () -> Unit)
            where T : Throwable {

        val throwable: Throwable? = try {
            f()
        } catch (e: Exception) {
            e
        } as? Throwable

        when (throwable) {
            null -> throw com.mckernant1.commons.assertions.AssertionException("Expected ${T::class.simpleName} but no exception was thrown")
            !is T -> throw com.mckernant1.commons.assertions.AssertionException("Exception ${throwable::class.simpleName} is not of expected type ${T::class.simpleName}")
        }
    }
}
