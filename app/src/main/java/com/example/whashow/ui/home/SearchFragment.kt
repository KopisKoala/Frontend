package com.example.whashow.ui.home

import android.view.View
import androidx.appcompat.widget.SearchView
import com.example.whashow.MainActivity
import com.example.whashow.R
import com.example.whashow.base.BaseFragment
import com.example.whashow.databinding.FragmentSearchBinding


class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).NoShow()
        (activity as MainActivity).binding.mainTitle.text="홈"
        (activity as MainActivity).binding.navigationMain.visibility=View.GONE
    }

    override fun initDataBinding() {
        super.initDataBinding()
        binding.search.isSubmitButtonEnabled = false
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })





    }

    override fun initAfterBinding() {
        super.initAfterBinding()
    }


}