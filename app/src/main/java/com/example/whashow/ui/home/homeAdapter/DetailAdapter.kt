package com.example.whashow.ui.home.homeAdapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.whashow.ui.home.CastingFragment
import com.example.whashow.ui.home.DetailReviewFragment
import com.example.whashow.ui.home.InfoFragment

class DetailAdapter (fragment: Fragment,  private val perfId: Int?, private val title:String?) : FragmentStateAdapter(fragment){
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> CastingFragment().apply {
                arguments = Bundle().apply {
                    putInt("perfId", perfId ?: 0)
                    putString("title", title ?:"")
                }
            }

            1 -> InfoFragment().apply {
                arguments = Bundle().apply {
                    putInt("perfId", perfId ?: 0)
                    putString("title", title ?:"")
                }
            }

            else -> DetailReviewFragment().apply {
                arguments = Bundle().apply {
                    putInt("perfId", perfId ?: 0)
                    putString("title", title ?:"")
                }
            }
        }
    }
}