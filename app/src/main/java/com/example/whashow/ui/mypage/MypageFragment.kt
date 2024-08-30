package com.example.whashow.ui.mypage

import android.graphics.Color
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.example.whashow.MainActivity
import com.example.whashow.R
import com.example.whashow.apiManager.ApiManager
import com.example.whashow.base.BaseFragment
import com.example.whashow.data.Info
import com.example.whashow.databinding.FragmentMypageBinding
import com.example.whashow.login.LocalDataSource
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        val Call: Call<Info> =
            ApiManager.mypageService.getInfo(
                "Bearer "+ LocalDataSource.getAccessToken()!!)
        // 비동기적으로 요청 수행
        Call.enqueue(object : Callback<Info> {
            override fun onResponse(
                call: Call<Info>,
                response: Response<Info>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()?.result
                    Log.d("정보 조회 서버", response.body()?.result.toString())
                    binding.name.text=data?.nickname
                    binding.rank.text=data?.userRank

                } else {
                    // 서버에서 오류 응답을 받은 경우 처리
                    Log.d("정보 조회 서버", response.toString())
                }

            }
            override fun onFailure(call: Call<Info>, t: Throwable) {
                // 통신 실패 처리
                Log.d("정보 조회 서버", t.message.toString())
            }

        })

        /* 탭과 뷰페이저를 연결, 여기서 새로운 탭을 다시 만드므로 레이아웃에서 꾸미지말고
        여기서 꾸며야함
         */
        TabLayoutMediator(binding.tabLayout, binding.vp) {tab, position ->
            when(position) {
                0 -> tab.text = "공연 달력"
                1 -> tab.text = "굿즈 상점"
                2 -> tab.text="내 배우"
            }
        }.attach()

        for (i in 0 until binding.tabLayout.tabCount) {
            val tab = (binding.tabLayout.getChildAt(0) as ViewGroup).getChildAt(i)
            val p = tab.layoutParams as ViewGroup.MarginLayoutParams
            p.setMargins(0, 0, 30, 0)
        }

    }

    override fun initAfterBinding() {
        super.initAfterBinding()

    }
}