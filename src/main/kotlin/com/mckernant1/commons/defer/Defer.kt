package com.mckernant1.commons.defer

/**
 * Similar to defer keyword in swift/golang where it defers actions to the end of the code block.
 *
 * This can be used in place of nesting closables.
 * Its less safe, but reduces clutter from nesting.
 * Its more safe than closing at the end of the method and forgetting
 *
 * Impl taken from https://stackoverflow.com/questions/51411018/kotlin-equivalent-of-swifts-defer-keyword
 *
 * Example using a temporary file
 *
 *      fun asdf(): Int? = defer { d ->
 *         val f = File(".")
 *         d.defer { f.delete() }
 *         f.list()?.size
 *     }
 */
class Deferrable {
    private val actions: MutableList<() -> Unit> = mutableListOf()

    fun defer(f: () -> Unit) {
        actions.add(f)
    }

    internal fun execute() {
        actions.forEach { it() }
    }
}

fun <T> defer(f: (Deferrable) -> T): T {
    val deferrable = Deferrable()
    try {
        return f(deferrable)
    } finally {
        deferrable.execute()
    }
}
