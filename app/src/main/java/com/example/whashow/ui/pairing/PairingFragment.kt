package com.example.whashow.ui.pairing

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.whashow.MainActivity
import com.example.whashow.R
import com.example.whashow.base.BaseFragment
import com.example.whashow.databinding.FragmentGenreBinding
import com.example.whashow.databinding.FragmentPairingBinding

class PairingFragment : BaseFragment<FragmentPairingBinding>(R.layout.fragment_pairing) {
    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).binding.mainTitle.text="페어링"
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