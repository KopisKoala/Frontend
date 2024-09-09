package com.example.whashow.ui.performance

import android.graphics.Color
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import com.example.whashow.MainActivity
import com.example.whashow.R
import com.example.whashow.apiManager.ApiManager
import com.example.whashow.base.BaseFragment
import com.example.whashow.data.RecommandPerformanceList
import com.example.whashow.databinding.FragmentPriceBinding
import com.example.whashow.login.LocalDataSource
import com.example.whashow.viewModel.PerformanceViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SelectPriceFragment : BaseFragment<FragmentPriceBinding>(R.layout.fragment_price) {
    private val recommandViewModel: PerformanceViewModel by activityViewModels()

    private val PriceArr = arrayListOf(
        "0원 ~ 5만원",
        "5만원 ~ 10만원",
        "10만원 ~ 15만원",
        "15만원 ~ 20만원",
        "20만원 ~ no limit"
    )

    override fun initStartView() {
        super.initStartView()
        // 장르 정보를 가져와서 UI 업데이트
        recommandViewModel.genre.observe(viewLifecycleOwner) { genre ->
            (activity as MainActivity).binding.backTitle.text = getGenreTitle(genre)
        }
        (activity as MainActivity).binding.toolbar.setBackgroundColor(Color.WHITE)
        (activity as MainActivity).ShowBackandTitle()
        (activity as MainActivity).binding.navigationMain.visibility = View.GONE
    }

    override fun initDataBinding() {
        super.initDataBinding()

        setPricePicker()

        binding.btnPrevious.setOnClickListener {
            (activity as MainActivity).onBackPressed()
        }
        binding.btnRecommandResult.setOnClickListener {
            // 선택된 가격 범위를 ViewModel에 저장
            val selectedPriceIndex = binding.pricePicker.value
            val (minPrice, maxPrice) = getPriceRange(selectedPriceIndex)
            Log.d("가격", minPrice.toString())
            Log.d("가격", maxPrice.toString())
            recommandViewModel.setPrice(minPrice, maxPrice)
            recommandViewModel.fetchPerformanceData()

            (activity as MainActivity).addFragment(
                PerformanceResultFragment()
            )

        }


    }

    private fun getGenreTitle(genre: Int?): String {
        return when (genre) {
            0 -> "뮤지컬"
            else -> "연극"
        }
    }

    private fun setPricePicker() {
        // NumberPicker 설정
        binding.pricePicker.apply {
            minValue = 0
            displayedValues = PriceArr.toTypedArray()
            maxValue = PriceArr.size - 1
            wrapSelectorWheel = false
        }
    }

    // PriceArr에서 선택된 인덱스에 해당하는 가격 범위를 파싱하여 반환
    private fun getPriceRange(index: Int): Pair<Int, Int> {
        return when (index) {
            0 -> 0 to 50000
            1 -> 50000 to 100000
            2 -> 100000 to 150000
            3 -> 150000 to 200000
            4 -> 200000 to Int.MAX_VALUE // 'no limit'일 경우 최대 값을 설정
            else -> 0 to 0
        }
    }

    override fun initAfterBinding() {
        super.initAfterBinding()
    }
}