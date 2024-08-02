package com.example.whashow.ui.mypage

import android.view.View
import android.widget.ArrayAdapter
import com.example.whashow.MainActivity
import com.example.whashow.R
import com.example.whashow.base.BaseFragment
import com.example.whashow.databinding.FragmentMakeReviewBinding


class MakeReviewFragment : BaseFragment<FragmentMakeReviewBinding>(R.layout.fragment_make_review) {
    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).binding.backTitle.text = "리뷰작성"
        (activity as MainActivity).ShowBackandTitle()
    }

    override fun initDataBinding() {
        super.initDataBinding()
        (activity as MainActivity).binding.navigationMain.visibility = View.GONE
        val yearSpinner = binding.yearSpinner
        val monthSpinner = binding.monthSpinner
        val daySpinner = binding.daySpinner

        // 연도 데이터 설정
        val years = arrayOf("2021", "2022", "2023")
        val yearAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, years)
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        yearSpinner.adapter = yearAdapter

        // 월 데이터 설정
        val months = arrayOf("1", "2", "3")
        val monthAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, months)
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        monthSpinner.adapter = monthAdapter

        // 일 데이터 설정
        val days = arrayOf("1", "2", "3")
        val dayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, days)
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        daySpinner.adapter = dayAdapter
    }

    override fun initAfterBinding() {
        super.initAfterBinding()
        (activity as MainActivity).binding.navigationMain.visibility = View.GONE
    }

}