package com.example.whashow.ui.pairing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.whashow.MainActivity
import com.example.whashow.R
import com.example.whashow.databinding.PairingBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.selects.select


class SelectBottomSheet : BottomSheetDialogFragment()  {

    private var binding: PairingBottomSheetBinding?=null
    private lateinit var selectListAdapter: SelectAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PairingBottomSheetBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val selectList = arrayListOf(
            Select(R.drawable.img_actor1,"손승연"),
            Select(R.drawable.img_actor2,"정선아"),
            Select(R.drawable.img_actor3,"옥주현"),
            Select(R.drawable.img_actor4,"나하나")
        )
        selectListAdapter = SelectAdapter(selectList)
        binding!!.selectRv.adapter = selectListAdapter
        binding!!.selectRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        selectListAdapter.setMyItemClickListener(object: SelectAdapter.MyItemClickListener{
            override fun onClick(position: Int) {
                selectListAdapter.notifyDataSetChanged()
            }
        })
        binding!!.selectResult.setOnClickListener {
            (activity as MainActivity).addFragment(PairingResultFragment())
        }
    }

    companion object {
        const val TAG = "BasicBottomModalSheet"
    }
}