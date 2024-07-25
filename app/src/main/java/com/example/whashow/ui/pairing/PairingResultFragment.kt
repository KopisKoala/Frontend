package com.example.whashow.ui.pairing

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.whashow.MainActivity
import com.example.whashow.R
import com.example.whashow.base.BaseFragment
import com.example.whashow.databinding.FragmentPairingResultBinding

class PairingResultFragment : BaseFragment<FragmentPairingResultBinding>(R.layout.fragment_pairing_result) {
    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).binding.backTitle.text="페어링 추천받기"
        (activity as MainActivity).ShowBackandTitle()
        (activity as MainActivity).binding.navigationMain.visibility=View.GONE
    }

    override fun initDataBinding() {
        super.initDataBinding()
    }

    override fun initAfterBinding() {
        super.initAfterBinding()

    }
}