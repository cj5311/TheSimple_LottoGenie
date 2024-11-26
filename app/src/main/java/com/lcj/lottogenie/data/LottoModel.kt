package com.lcj.lottogenie.data

import com.google.gson.annotations.SerializedName

data class LottoModel(
    @SerializedName("bnusNo")
    val bnusNo: Int,
    @SerializedName("drwNo")
    val drwNo: Int,
    @SerializedName("drwNoDate")
    val drwNoDate: String,
    @SerializedName("drwtNo1")
    val drwtNo1: Int,
    @SerializedName("drwtNo2")
    val drwtNo2: Int,
    @SerializedName("drwtNo3")
    val drwtNo3: Int,
    @SerializedName("drwtNo4")
    val drwtNo4: Int,
    @SerializedName("drwtNo5")
    val drwtNo5: Int,
    @SerializedName("drwtNo6")
    val drwtNo6: Int,
    @SerializedName("firstAccumamnt")
    val firstAccumamnt: Long,
    @SerializedName("firstPrzwnerCo")
    val firstPrzwnerCo: Int,
    @SerializedName("firstWinamnt")
    val firstWinamnt: Int,
    @SerializedName("returnValue")
    val returnValue: String,
    @SerializedName("totSellamnt")
    val totSellamnt: Long
) {
    fun toLottoData(): LottoData {
        return if (returnValue == "fail") {
            LottoData()
        } else {
            LottoData(
                gameNumber = drwNo,
                winNumbers = listOf(drwtNo1, drwtNo2, drwtNo3, drwtNo4, drwtNo5, drwtNo6),
                bonusNumber = bnusNo,
                data = drwNoDate,
                totalWinPrize = totSellamnt,
                winPrize = firstWinamnt
            )
        }
    }
}