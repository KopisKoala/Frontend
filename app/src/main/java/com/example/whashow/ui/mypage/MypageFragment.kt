package com.example.whashow.ui.mypage

import android.view.View
import com.example.whashow.MainActivity
import com.example.whashow.R
import com.example.whashow.base.BaseFragment
import com.example.whashow.databinding.FragmentMypageBinding

class MypageFragment : BaseFragment<FragmentMypageBinding>(R.layout.fragment_mypage) {
    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).binding.mainTitle.text="마이"
        (activity as MainActivity).ShowTitle()
        (activity as MainActivity).binding.navigationMain.visibility=View.VISIBLE
    }

    override fun initDataBinding() {
        super.initDataBinding()
    }

    override fun initAfterBinding() {
        super.initAfterBinding()

    }
}