package com.github.mckernant1.extensions.convert

import com.fasterxml.jackson.databind.ObjectMapper


object MapConverters {
    inline fun <reified T> Map<String, Any>.toObject(
        objectMapper: ObjectMapper = ObjectMapper()
    ): T =
        objectMapper.convertValue(this, T::class.java)

    inline fun <reified T : Any> Sequence<Map<String, Any>>.mapToObject(
        objectMapper: ObjectMapper = ObjectMapper()
    ): Sequence<T> = map {
        it.toObject(objectMapper)
    }
}
