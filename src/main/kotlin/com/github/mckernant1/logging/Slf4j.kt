package com.github.mckernant1.logging

import org.slf4j.Logger
import org.slf4j.LoggerFactory

object Slf4j {

    /**
     * Create SLF4J logger on the given class
     */
    inline fun <reified T> T.logger(): Logger = LoggerFactory.getLogger(T::class.java)

    /**
     * Create SLF4J logger for the given name
     */
    fun logger(name: String): Logger = LoggerFactory.getLogger(name)
}
