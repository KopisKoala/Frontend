package com.example.whashow.ui.recommand

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.whashow.MainActivity
import com.example.whashow.R
import com.example.whashow.base.BaseFragment
import com.example.whashow.databinding.FragmentRecommandResultBinding

class RecommandResultFragment : BaseFragment<FragmentRecommandResultBinding>(R.layout.fragment_recommand_result) {
    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).binding.mainTitle.text="공연 추천"
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