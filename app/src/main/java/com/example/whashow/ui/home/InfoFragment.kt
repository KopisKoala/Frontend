package com.example.whashow.ui.home

import android.view.View
import androidx.fragment.app.viewModels
import com.example.whashow.MainActivity
import com.example.whashow.R
import com.example.whashow.base.BaseFragment
import com.example.whashow.databinding.FragmentInfoBinding

class InfoFragment : BaseFragment<FragmentInfoBinding>(R.layout.fragment_info) {

    private val viewModel: PerformanceDetailViewModel by viewModels()

    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).binding.navigationMain.visibility = View.GONE

        val performanceId = 123 // 실제 공연 ID로 교체 필요
        viewModel.fetchPerformanceData(performanceId)
    }

    override fun initDataBinding() {
        super.initDataBinding()

        viewModel.performanceDetail.observe(viewLifecycleOwner) { detail ->
            // 장소, 도로명 주소, 공연 기간, 공연 시간을 뷰에 설정
            binding.tvLocation.text = detail.hallName
            binding.tvAddress.text = detail.streetAddress
            binding.tvPeriod.text = "${detail.startDate} ~ ${detail.endDate}"
            binding.tvTime.text = detail.runtime

            // 편의시설의 Y/N 여부에 따라 visibility 설정
            toggleVisibility(binding.tvRestaurant, binding.ivRestaurant, detail.isRestaurant)
            toggleVisibility(binding.tvConvenienceStore, binding.ivConvenienceStore, detail.isStore)
            toggleVisibility(binding.tvCafe, binding.ivCafe, detail.isCafe)
            toggleVisibility(binding.tvPlayroom, binding.ivPlayroom, detail.isNolibang)
            toggleVisibility(binding.tvNursingRoom, binding.ivNursingRoom, detail.isSuyu)
            toggleVisibility(binding.tvParking, binding.ivParking, detail.isParking)
        }


    }

    private fun toggleVisibility(textView: View, imageView: View, status: String) {
        val visibility = if (status == "Y") View.VISIBLE else View.GONE
        textView.visibility = visibility
        imageView.visibility = visibility
    }

    override fun initAfterBinding() {
        super.initAfterBinding()
        (activity as MainActivity).binding.navigationMain.visibility = View.GONE
    }
}
