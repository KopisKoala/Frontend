package com.example.whashow.ui.home

import Performance
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.whashow.MainActivity
import com.example.whashow.R
import com.example.whashow.base.BaseFragment
import com.example.whashow.databinding.FragmentSearchBinding
import com.example.whashow.ui.home.Adapter.ActorAdapter
import com.example.whashow.ui.home.Adapter.PerformanceAdapter


class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {
    private val searchViewModel : SearchViewModel by viewModels()
    private lateinit var actorListAdapter: ActorAdapter
    private lateinit var performanceListAdapter: PerformanceAdapter

    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).NoShow()
        (activity as MainActivity).binding.mainTitle.text="홈"
        (activity as MainActivity).binding.navigationMain.visibility=View.GONE
        searchViewModel.fetchSearchHome("")
//        binding.actorTitle.visibility = View.GONE
//        binding.performanceTitle.visibility = View.GONE
//        binding.rcActor.visibility = View.GONE
//        binding.rcPerformance.visibility = View.GONE
    }

    override fun initDataBinding() {
        super.initDataBinding()
        binding.rcActor.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rcPerformance.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        actorListAdapter = ActorAdapter(arrayListOf())
        binding.rcActor.adapter = actorListAdapter
        performanceListAdapter = PerformanceAdapter(arrayListOf())
        binding.rcPerformance.adapter = performanceListAdapter

        performanceListAdapter.setMyItemClickListener(object : PerformanceAdapter.MyItemClickListener {
            override fun onItemClick(performance: Performance) {
                val fragment = PerformanceDetailFragment().apply {
                    arguments = Bundle().apply {
                        putInt("perfId", performance.id)
                        putString("title", performance.title)
                    }
                }
                (activity as MainActivity).addFragment(fragment)
            }


        })

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    searchViewModel.fetchSearchHome(query)

                    searchViewModel.actorList.observe(viewLifecycleOwner, Observer { actor ->
                        actorListAdapter.updateActors(actor)
                        binding.actorTitle.text = "배우 ${actor.size}건"
                        binding.actorTitle.visibility = if (actor.isEmpty()) View.GONE else View.VISIBLE
                        binding.rcActor.visibility = if (actor.isEmpty()) View.GONE else View.VISIBLE
                    })

                    searchViewModel.performanceList.observe(viewLifecycleOwner, Observer { performance ->
                        performanceListAdapter.updatePerformances(performance)
                        binding.performanceTitle.text = "공연 ${performance.size}건"
                        binding.performanceTitle.visibility = if (performance.isEmpty()) View.GONE else View.VISIBLE
                        binding.rcPerformance.visibility = if (performance.isEmpty()) View.GONE else View.VISIBLE
                    })
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    binding.actorTitle.visibility = View.GONE
                    binding.performanceTitle.visibility = View.GONE
                    binding.rcActor.visibility = View.GONE
                    binding.rcPerformance.visibility = View.GONE
                }
                return true
            }
        })

    }

    override fun initAfterBinding() {
        super.initAfterBinding()
    }
}