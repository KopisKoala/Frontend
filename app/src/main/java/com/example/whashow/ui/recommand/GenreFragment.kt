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
import com.example.whashow.databinding.FragmentGenreBinding


class GenreFragment : BaseFragment<FragmentGenreBinding>(R.layout.fragment_genre) {
    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).binding.backTitle.text="추천"
        (activity as MainActivity).binding.backTitle.setTextColor(Color.WHITE)
        (activity as MainActivity).ShowBackandTitle()
        (activity as MainActivity).binding.navigationMain.visibility=View.GONE
    }

    override fun initDataBinding() {
        super.initDataBinding()
        binding.genreMusical.setOnClickListener {
            (activity as MainActivity).addFragment(DayandPlaceFragment())
        }
        binding.genrePlay.setOnClickListener {
            (activity as MainActivity).addFragment(DayandPlaceFragment())
        }
    }

    override fun initAfterBinding() {
        super.initAfterBinding()
        (activity as MainActivity).binding.backTitle.setTextColor(Color.BLACK)

    }
}