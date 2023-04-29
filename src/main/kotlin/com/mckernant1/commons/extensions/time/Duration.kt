package com.mckernant1.commons.extensions.time

import org.apache.commons.lang3.time.DurationFormatUtils
import java.time.Duration

object DurationFormat {
    fun Duration.format(pattern: String = "H:mm:ss", padWithZeros: Boolean = true): String =
        DurationFormatUtils.formatDuration(this.toMillis(), pattern, padWithZeros)
}
