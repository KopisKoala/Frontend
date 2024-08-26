package com.example.whashow.ui.home

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.example.whashow.MainActivity
import com.example.whashow.R
import com.example.whashow.base.BaseFragment
import com.example.whashow.data.PerformanceDetailResult
import com.example.whashow.databinding.FragmentInfoBinding

class InfoFragment : BaseFragment<FragmentInfoBinding>(R.layout.fragment_info) {

    private val viewModel: PerformanceDetailViewModel by viewModels()
    private var perfId: Int? = null

    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).binding.navigationMain.visibility = View.GONE

        perfId = arguments?.getInt("perfId")
        perfId?.let {
            viewModel.fetchPerformanceData(it)
        }
    }

    override fun initDataBinding() {
        super.initDataBinding()

        viewModel.performanceDetail.observe(viewLifecycleOwner) { detail ->

            binding.tvLocation.text = detail.hallName
            binding.tvAddress.text = detail.streetAddress
            binding.tvPeriod.text = "${detail.startDate} ~ ${detail.endDate}"
            binding.tvTime.text = detail.runtime

            // 편의시설 아이템 동적 추가
            addFacilityItems(detail)
        }
    }

    private fun addFacilityItems(detail: PerformanceDetailResult) {
        // GridLayout 초기화
        binding.glFacilities.removeAllViews() // 기존 뷰 제거

        // 편의시설 항목 추가
        if (detail.isRestaurant == "Y") {
            addFacilityItem(R.drawable.ic_restaurant, "식당")
        }
        if (detail.isStore == "Y") {
            addFacilityItem(R.drawable.ic_convenience_store, "편의점")
        }
        if (detail.isCafe == "Y") {
            addFacilityItem(R.drawable.ic_cafe, "카페")
        }
        if (detail.isNolibang == "Y") {
            addFacilityItem(R.drawable.ic_playroom, "놀이방")
        }
        if (detail.isSuyu == "Y") {
            addFacilityItem(R.drawable.ic_nursing_room, "수유실")
        }
        if (detail.isParking == "Y") {
            addFacilityItem(R.drawable.ic_parking, "주차시설")
        }
    }

    private fun addFacilityItem(iconResId: Int, text: String) {
        val itemView = layoutInflater.inflate(R.layout.item_facility, binding.glFacilities, false)
        val ivIcon = itemView.findViewById<ImageView>(R.id.iv_icon)
        val tvText = itemView.findViewById<TextView>(R.id.tv_text)

        ivIcon.setImageResource(iconResId)
        tvText.text = text

        binding.glFacilities.addView(itemView)
    }

    override fun initAfterBinding() {
        super.initAfterBinding()
        (activity as MainActivity).binding.navigationMain.visibility = View.GONE
    }
}
