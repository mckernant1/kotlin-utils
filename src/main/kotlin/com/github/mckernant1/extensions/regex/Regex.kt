package com.github.mckernant1.extensions.regex

/**
 * This operator function helps with `when` statements
 *
 * ```
 * when("Hello world!") {
 *     in Regex("Hello.*!") -> println("This works")
 * }
 * ```
 */
operator fun Regex.contains(c: CharSequence): Boolean = this.containsMatchIn(c)
