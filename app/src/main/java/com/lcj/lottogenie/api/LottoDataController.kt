package com.lcj.lottogenie.api

import android.util.Log
import com.lcj.lottogenie.data.LottoData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "LottoController"

//LottoService 인터페이스에 정의된 getLottoNumber함수 실행시켜주는 기능 정의
class LottoController(private val service: LottoService = RetrofitInstance.service) {
    fun getLottoNumber(num: Int, onSuccess: (LottoData) -> Unit, onError: (String) -> Unit) {
        // 비동기 동작제어 위해 CoroutineScope 사용
        CoroutineScope(Dispatchers.Main).launch {
            runCatching {
                val result = withContext(Dispatchers.IO){service.getLottoNumber(num = num)}
                onSuccess(result.toLottoData())
            }.onFailure { // 예외처리
                Log.e(TAG, "getLottoNumber() failed! : ${it.message}")
                onError(it.message ?: "Unknown error")
            }
        }
    }
}
