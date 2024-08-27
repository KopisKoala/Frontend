package com.example.whashow.ui.mypage

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class MypageVPAdapter (fragment : Fragment) : FragmentStateAdapter(fragment){
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PerformanceCalendarFragment()
            1 -> GoodsStoreFragment()
            else -> MyActorFragment()
        }
    }
}