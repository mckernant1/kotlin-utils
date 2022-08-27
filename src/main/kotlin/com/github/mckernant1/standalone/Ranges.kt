package com.github.mckernant1.standalone


/**
 * this <= i <= other
 */
infix fun Int.ge_le(other: Int): IntProgression = IntProgression.fromClosedRange(
    rangeStart = this,
    rangeEnd = other,
    step = 1
)

/**
 * this < i <= other
 */
infix fun Int.gt_le(other: Int): IntProgression = IntProgression.fromClosedRange(
    rangeStart = this + 1,
    rangeEnd = other,
    step = 1
)

/**
 * this <= i < other
 */
infix fun Int.ge_lt(other: Int): IntProgression = IntProgression.fromClosedRange(
    rangeStart = this,
    rangeEnd = other - 1,
    step = 1
)


/**
 * this < i < other
 */
infix fun Int.gt_lt(other: Int): IntProgression = IntProgression.fromClosedRange(
    rangeStart = this + 1,
    rangeEnd = other - 1,
    step = 1
)

/**
 * this >= i >= other
 */
infix fun Int.le_ge(other: Int): IntProgression = IntProgression.fromClosedRange(
    rangeStart = this,
    rangeEnd = other,
    step = -1
)

/**
 * this >= i > other
 */
infix fun Int.le_gt(other: Int): IntProgression = IntProgression.fromClosedRange(
    rangeStart = this,
    rangeEnd = other + 1,
    step = -1
)

/**
 * this > i >= other
 */
infix fun Int.lt_ge(other: Int): IntProgression = IntProgression.fromClosedRange(
    rangeStart = this - 1,
    rangeEnd = other,
    step = -1
)

/**
 * this > i > other
 */
infix fun Int.lt_gt(other: Int): IntProgression = IntProgression.fromClosedRange(
    rangeStart = this - 1,
    rangeEnd = other + 1,
    step = -1
)
