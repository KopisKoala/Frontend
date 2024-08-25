package com.example.whashow.ui.home

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.whashow.MainActivity
import com.example.whashow.R
import com.example.whashow.base.BaseFragment
import com.example.whashow.databinding.FragmentCastingBinding
import com.example.whashow.ui.home.Adapter.CastingAdapter


class CastingFragment : BaseFragment<FragmentCastingBinding>(R.layout.fragment_casting) {
    private val viewModel: PerformanceDetailViewModel by viewModels()
    private lateinit var castingAdapter: CastingAdapter

    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).binding.navigationMain.visibility = View.GONE

    }

    override fun initDataBinding() {
        super.initDataBinding()
        (activity as MainActivity).binding.navigationMain.visibility = View.GONE

        viewModel.actorList.observe(viewLifecycleOwner, Observer { actorList ->
            castingAdapter.list.clear()
            castingAdapter.list.addAll(actorList)
            castingAdapter.notifyDataSetChanged()
        })

        viewModel.fetchPerformanceData(performanceId = 1)

    }

    override fun initAfterBinding() {
        super.initAfterBinding()
        (activity as MainActivity).binding.navigationMain.visibility = View.GONE

        binding.rvCasting.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = castingAdapter
        }
    }
}