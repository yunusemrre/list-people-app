package com.sccase.listpeople.util

import kotlin.random.Random

object RandomUtils {

    fun generateRandomInt(range: ClosedRange<Int>): Int = Random.nextInt(range.start, range.endInclusive)

    fun generateRandomDouble(range: ClosedRange<Double>): Double = Random.nextDouble(range.start, range.endInclusive)

    fun roll(probability: Double): Boolean {
        val random = Random.nextDouble(0.0, 1.0)
        return random <= probability
    }
}