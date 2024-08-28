package com.example.whashow.ui.mypage

import android.view.View
import com.example.whashow.MainActivity
import com.example.whashow.R
import com.example.whashow.base.BaseFragment
import com.example.whashow.databinding.FragmentMychatsBinding
import com.google.android.material.tabs.TabLayoutMediator

class MychatsFragment : BaseFragment<FragmentMychatsBinding>(R.layout.fragment_mychats) {

    private val information = arrayListOf("공연 채팅방", "오픈 채팅방")
    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).binding.backTitle.text = "내 채팅방"
        (activity as MainActivity).ShowBackandTitle()
    }

    override fun initDataBinding() {
        super.initDataBinding()
        (activity as MainActivity).binding.navigationMain.visibility = View.GONE

        val mychatAdapter = MychatsAdapte(this)
        binding.mychatVp.adapter = mychatAdapter
        TabLayoutMediator(binding.mychatTb, binding.mychatVp){
            tab, position ->
            tab.text = information[position]
        }.attach()


    }

    override fun initAfterBinding() {
        super.initAfterBinding()
        (activity as MainActivity).binding.navigationMain.visibility = View.GONE
    }

}