package com.example.whashow.ui.home

import android.view.View
import com.example.whashow.MainActivity
import com.example.whashow.R
import com.example.whashow.base.BaseFragment
import com.example.whashow.databinding.FragmentPerformanceDetailBinding
import com.example.whashow.ui.home.Adapter.DetailAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson


class PerformanceDetailFragment : BaseFragment<FragmentPerformanceDetailBinding>(R.layout.fragment_performance_detail) {

    private val information = arrayListOf("캐스팅", "상세 정보", "후기")


    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).ShowBack()
        arguments?.getString("banner")?.let {
            val banner = Gson().fromJson(it, Banner::class.java)
            binding.ivItem.setImageResource(banner.img)
        }
    }

    override fun initDataBinding() {
        super.initDataBinding()
        (activity as MainActivity).binding.navigationMain.visibility = View.GONE
        val detailAdapter = DetailAdapter(this)
        binding.vpDetail.adapter = detailAdapter // 수정된 부분
        TabLayoutMediator(binding.tbDetail, binding.vpDetail) { tab, position ->
            tab.text = information[position]
        }.attach()
    }
    override fun initAfterBinding() {
        super.initAfterBinding()
        (activity as MainActivity).binding.navigationMain.visibility = View.GONE
    }
}