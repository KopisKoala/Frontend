package com.example.whashow.ui.home

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.whashow.MainActivity
import com.example.whashow.R
import com.example.whashow.base.BaseFragment
import com.example.whashow.databinding.FragmentDetailReviewBinding

class DetailReviewFragment: BaseFragment<FragmentDetailReviewBinding>(R.layout.fragment_detail_review) {

    private val viewModel: PerformanceDetailViewModel by viewModels()
    private var perfId: Int? = null


    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).binding.navigationMain.visibility = View.GONE

        perfId = arguments?.getInt("perfId")
        perfId?.let {
            viewModel.fetchPerformanceData(it)
        }
    }
    override fun initDataBinding() {
        super.initDataBinding()
        (activity as MainActivity).binding.navigationMain.visibility = View.GONE


        viewModel.hashtags.observe(viewLifecycleOwner, Observer { hashtags ->
            if (hashtags.isNotEmpty()) {
                binding.hashtag1.text = hashtags.getOrNull(0)?.let { "#$it" } ?: ""
                binding.hashtag2.text = hashtags.getOrNull(1)?.let { "#$it" } ?: ""
                binding.hashtag3.text = hashtags.getOrNull(2)?.let { "#$it" } ?: ""
            }
        })

        viewModel.rating.observe(viewLifecycleOwner, Observer { rating ->
            binding.ratingBar.rating = rating
            binding.ratingNum.text = rating.toString()
        })


    }
    override fun initAfterBinding() {
        super.initAfterBinding()
        (activity as MainActivity).binding.navigationMain.visibility = View.GONE
    }
}
