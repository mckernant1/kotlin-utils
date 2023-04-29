package com.mckernant1.commons.lazy

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ResetableLazyTest {

    @Test
    fun testResetableLazy() {

        val lazyManager = resettableManager()
        var x = 0
        val l by resettableLazy(lazyManager) {
            x += 1
            x
        }

        assertEquals(1, l)
        assertEquals(1, l)
        assertEquals(1, l)

        lazyManager.reset()

        assertEquals(2, l)
        assertEquals(2, l)
        assertEquals(2, l)

    }

}
