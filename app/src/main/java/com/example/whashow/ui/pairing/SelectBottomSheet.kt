package com.example.whashow.ui.pairing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.whashow.R
import com.example.whashow.databinding.PairingBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class SelectBottomSheet : BottomSheetDialogFragment()  {

    private var binding: PairingBottomSheetBinding?=null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PairingBottomSheetBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    companion object {
        const val TAG = "BasicBottomModalSheet"
    }
}