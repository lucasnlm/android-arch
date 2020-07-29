package dev.lucasnlm.arch.info.models

data class ClockInfo(
    val clocks: List<Int>,
    val maxClock: Int,
    val minClock: Int
)