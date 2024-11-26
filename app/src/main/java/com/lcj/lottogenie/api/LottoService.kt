package com.lcj.lottogenie.api

import com.lcj.lottogenie.data.LottoModel
import retrofit2.http.GET
import retrofit2.http.Query

// 서버에서 결과값 가져오는 기능
interface LottoService {
    @GET("common.do")
    suspend fun getLottoNumber(
        @Query("drwNo") num: Int,
        @Query("method") method: String = "getLottoNumber"
    ): LottoModel
}