package com.example.whashow.ui.recommand

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.whashow.MainActivity
import com.example.whashow.R
import com.example.whashow.base.BaseFragment
import com.example.whashow.databinding.FragmentPriceBinding

class PriceFragment : BaseFragment<FragmentPriceBinding>(R.layout.fragment_price) {

    private val PriceArr = arrayListOf(
        "0원 ~ 5만원",
        "5만원 ~ 10만원",
        "10만원 ~ 15만원",
        "15만원 ~ 20만원",
        "20만원 ~ no limit"
    )
    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).binding.backTitle.text="뮤지컬"
        (activity as MainActivity).ShowBackandTitle()
        (activity as MainActivity).binding.navigationMain.visibility=View.GONE
    }

    override fun initDataBinding() {
        super.initDataBinding()

        setPricePicker()

        binding.btnPrevious.setOnClickListener {
            (activity as MainActivity).onBackPressed()
        }
        binding.btnRecommandResult.setOnClickListener {
            (activity as MainActivity).addFragment(RecommandResultFragment())
        }



    }

    private fun setPricePicker() {
        // max 인 층 보여주고 size 만 조절
        binding.pricePicker.let {
            it.minValue = 0
            it.displayedValues = PriceArr.toTypedArray()
            it.maxValue = 4
            it.wrapSelectorWheel = false
        }
    }

    override fun initAfterBinding() {
        super.initAfterBinding()
    }
}