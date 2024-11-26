package com.lcj.lottogenie.data

private const val EMPTY_STRING = 0

data class LottoData(
    val gameNumber: Int = 0,
    val winNumbers: List<Int> = emptyList(),
    val bonusNumber: Int = 0,
    val totalWinPrize: Long = 0,
    val winPrize: Int = 0,
    val winnerCount: Int = 0,
    val data: String = ""
)