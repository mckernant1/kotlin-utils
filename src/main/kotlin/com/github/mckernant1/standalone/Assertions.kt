package com.github.mckernant1.standalone

@Throws(IllegalStateException::class)
fun assertEnvironmentVariablesExist(vararg variables: String) {
    val unSet = variables.asSequence()
        .map { it to (System.getenv(it) ?: null) }
        .filter { (_, value) -> value == null }
        .map { (key, _) -> key }
        .toSet()

    if (unSet.isNotEmpty()) {
        throw IllegalStateException("Environment variables '$unSet' are not defined")
    }
}
