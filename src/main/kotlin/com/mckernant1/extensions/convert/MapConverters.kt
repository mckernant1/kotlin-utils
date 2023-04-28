package com.mckernant1.extensions.convert

import com.fasterxml.jackson.databind.ObjectMapper


object MapConverters {
    inline fun <reified T> Map<out Any, Any>.toObject(
        objectMapper: ObjectMapper = ObjectMapper()
    ): T =
        objectMapper.convertValue(this, T::class.java)

    inline fun <reified T : Any> Sequence<Map<out Any, Any>>.mapToObject(
        objectMapper: ObjectMapper = ObjectMapper()
    ): Sequence<T> = map {
        it.toObject(objectMapper)
    }
}
