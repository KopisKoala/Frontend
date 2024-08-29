package com.example.whashow.ui.home

import android.view.View
import com.example.whashow.MainActivity
import com.example.whashow.R
import com.example.whashow.base.BaseFragment
import com.example.whashow.databinding.FragmentEtiquetteBinding

class EtiquetteFragment : BaseFragment<FragmentEtiquetteBinding>(R.layout.fragment_etiquette) {
    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).ShowBackEq()
    }

    override fun initDataBinding() {
        super.initDataBinding()
        (activity as MainActivity).binding.navigationMain.visibility = View.GONE
    }

    override fun initAfterBinding() {
        super.initAfterBinding()
        (activity as MainActivity).binding.navigationMain.visibility = View.GONE
    }

}