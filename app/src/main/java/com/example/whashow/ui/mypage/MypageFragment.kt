package com.example.whashow.ui.mypage

import android.graphics.Color
import android.view.View
import com.example.whashow.MainActivity
import com.example.whashow.R
import com.example.whashow.base.BaseFragment
import com.example.whashow.databinding.FragmentMypageBinding
import com.example.whashow.databinding.FragmentPairingBinding
import com.example.whashow.databinding.FragmentPerformanceCalendarBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

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

        binding.tabLayout.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
        // 뷰페이저에 어댑터 연결
        binding.vp.adapter = MypageVPAdapter(this)
        binding.vp.isUserInputEnabled=false

        /* 탭과 뷰페이저를 연결, 여기서 새로운 탭을 다시 만드므로 레이아웃에서 꾸미지말고
        여기서 꾸며야함
         */
        TabLayoutMediator(binding.tabLayout, binding.vp) {tab, position ->
            when(position) {
                0 -> tab.text = "공연 달력"
                1 -> tab.text = "굿즈 상점"
            }
        }.attach()

    }

    override fun initAfterBinding() {
        super.initAfterBinding()

    }
}