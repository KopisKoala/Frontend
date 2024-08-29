package com.example.whashow.ui.home

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.whashow.MainActivity
import com.example.whashow.R
import com.example.whashow.base.BaseFragment
import com.example.whashow.databinding.FragmentPerformanceDetailBinding
import com.example.whashow.ui.home.Adapter.DetailAdapter
import com.google.android.material.tabs.TabLayoutMediator


class PerformanceDetailFragment : BaseFragment<FragmentPerformanceDetailBinding>(R.layout.fragment_performance_detail) {

    private val information = arrayListOf("캐스팅", "상세 정보", "후기")
    private var perfId: Int? = null
    private var title: String? = null
    private val viewModel by viewModels<PerformanceDetailViewModel>()


    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).ShowBack()
        perfId = arguments?.getInt("perfId")
        title = arguments?.getString("title")
        (activity as MainActivity).binding.mainTitle.text=title
    }

    override fun initDataBinding() {
        super.initDataBinding()
        (activity as MainActivity).binding.navigationMain.visibility = View.GONE
        (activity as MainActivity).binding.mainTitle.text=title
        val detailAdapter = DetailAdapter(this, perfId, title)
        binding.vpDetail.adapter = detailAdapter

        TabLayoutMediator(binding.tbDetail, binding.vpDetail) { tab, position ->
            tab.text = information[position]
        }.attach()

        viewModel.performanceDetail.observe(viewLifecycleOwner) { detail ->
            Glide.with(this)
                .load(detail.poster)
                .placeholder(R.drawable.img_detail)
                .into(binding.ivItem)
        }

        perfId?.let {
            viewModel.fetchPerformanceData(it)
        }

        binding.btnReservation.setOnClickListener {
            viewModel.ticketingLink.value?.let { link ->
                openWebPage(link)
            }
        }

    }
    override fun initAfterBinding() {
        super.initAfterBinding()
        (activity as MainActivity).binding.navigationMain.visibility = View.GONE
    }

    private fun openWebPage(url: String) {
        val webpage: Uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, webpage)
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(intent)
        }
    }
}