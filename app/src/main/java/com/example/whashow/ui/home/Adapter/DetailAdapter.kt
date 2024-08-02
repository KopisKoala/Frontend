package com.example.whashow.ui.home.Adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.whashow.ui.home.CastingFragment
import com.example.whashow.ui.home.DetailReviewFragment
import com.example.whashow.ui.home.InfoFragment

class DetailAdapter (fragment: Fragment) : FragmentStateAdapter(fragment){
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> CastingFragment()// 캐스팅
            1 -> InfoFragment()// 상세 정보
            else -> DetailReviewFragment() //후기
        }
    }

}