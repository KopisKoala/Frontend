package com.example.whashow.ui.mypage

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class MychatsAdapte(fragment:Fragment) : FragmentStateAdapter(fragment){
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> ChatsPerformanceFragment()// 공연채팅방
            else ->  ChatsOpenFragment() //오픈채팅방
        }
    }

}