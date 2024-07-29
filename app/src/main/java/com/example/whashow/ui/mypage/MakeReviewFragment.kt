package com.example.whashow.ui.mypage

import android.view.View
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
    }

    override fun initAfterBinding() {
        super.initAfterBinding()
        (activity as MainActivity).binding.navigationMain.visibility = View.GONE
    }

}