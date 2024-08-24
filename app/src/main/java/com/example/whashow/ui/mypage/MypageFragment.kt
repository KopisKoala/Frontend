package com.example.whashow.ui.mypage

import android.graphics.Color
import android.view.View
import com.example.whashow.MainActivity
import com.example.whashow.R
import com.example.whashow.base.BaseFragment
import com.example.whashow.databinding.FragmentMypageBinding

class MypageFragment : BaseFragment<FragmentMypageBinding>(R.layout.fragment_mypage) {
    override fun initStartView() {
        super.initStartView()
        //배경 흰색
        (activity as MainActivity).binding.toolbar.setBackgroundColor(Color.WHITE)
        (activity as MainActivity).binding.btnBack.setImageResource(R.drawable.btn_back)
        (activity as MainActivity).binding.backTitle.setTextColor(Color.BLACK)

        (activity as MainActivity).binding.mainTitle.text="마이"
        (activity as MainActivity).ShowTitle()
        (activity as MainActivity).binding.navigationMain.visibility=View.VISIBLE
    }

    override fun initDataBinding() {
        super.initDataBinding()
        binding.toMember.setOnClickListener {
            (activity as MainActivity).addFragment(MemberInfoFragment())
        }
    }

    override fun initAfterBinding() {
        super.initAfterBinding()

    }
}