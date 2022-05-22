package com.github.mckernant1.standalone

@Throws(IllegalStateException::class)
fun validateEnvironmentVariablesExist(vararg variables: String) {
    val unSet = variables.map {
        it to (System.getenv(it) ?: null)
    }.filter { (_, value) ->
        value == null
    }.map { (key, _) -> key }

    if (unSet.isNotEmpty()) {
        throw IllegalStateException("Environment variables '$unSet' are not defined")
    }
}
