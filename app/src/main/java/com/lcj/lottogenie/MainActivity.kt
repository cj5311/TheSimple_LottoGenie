package com.lcj.lottogenie

import android.os.Bundle
import android.text.Html
import android.text.Html.FROM_HTML_MODE_COMPACT
import android.text.Spanned
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.lcj.lottogenie.api.LottoController
import com.lcj.lottogenie.data.LottoData
import com.lcj.lottogenie.databinding.ActivityMainBinding
import com.lcj.lottogenie.ui.theme.LottoGenieTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Random
import kotlin.dec
import kotlin.inc
import kotlin.toString

class MainActivity : ComponentActivity() {
    private var number: Int = 1102
    private lateinit var binding: ActivityMainBinding
    private val lottoController by lazy { LottoController() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //바인딩객체 생성
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fetchLottoData(number)

        with(binding) {
            //검색하기 버튼 눌렀을때 : 데이터 갱신하기
            searchButton.setOnClickListener {
                number = searchView.text.toString().toInt()
                fetchLottoData(number)
            }
            //왼쪽 버튼 눌렀을때 : 데이터 갱신하기
            arrowLeft.setOnClickListener {
                number = number.dec()
                fetchLottoData(number)
            }
            // 오른쪽 버튼 눌렀을때 : 데이터 갱신
            arrowRight.setOnClickListener {
                number = number.inc()
                fetchLottoData(number)
            }
        }
    }
    //데이터 갱신: 컨트롤러통해 데이터 가져오고 UI 업데이트
    private fun fetchLottoData(number: Int) {
        lottoController.getLottoNumber(number, { lottoData ->
            updateUI(lottoData)
        }, { _ ->
            Toast.makeText(
                this@MainActivity,
                "로또 정보를 가져오는데 실패했습니다. 잠시 후 다시 이용해주세요.",
                Toast.LENGTH_LONG
            ).show()
        })
    }
    // UI 갱신
    private fun updateUI(data: LottoData) {
        with(binding) {
            //1100회 당첨결과
            winResultTextView.text = getHtmlText(R.string.winning_result, number.toString())
            //날짜 출력
            dateView.text = data.data
            
            // 리스트 정의
            val listWinNumberView =
                listOf(number1Text, number2Text, number3Text, number4Text, number5Text, number6Text)
            
            // 리스트에 데이터 맵핑
            listWinNumberView.mapIndexed { index, tv ->
                val numbers = data.winNumbers
                tv.text = (if (numbers.isNotEmpty()) numbers[index] else "") as CharSequence?
            }

            //보너스숫자에 데이터 갱신
            numberBonusText.text = data.bonusNumber.toString()

            // 금액 출력
            val prize = data.totalWinPrize.toString().toBillion()
            winAmountText.text = getHtmlText(R.string.win_prize, prize)
            val prizeOne = data.winPrize.toString().toBillion()
            win1AmountText.text = getHtmlText(R.string.win_prize_1, prizeOne)
        }
    }

    private fun String.toBillion(): String {
        return if (this != "") (this.toLong() / 100_000_000).toString() else this
    }

    private fun getHtmlText(@StringRes id: Int, data: String): Spanned {
        val text: String = getString(id, data)
        return Html.fromHtml(text, FROM_HTML_MODE_COMPACT)
    }

}



