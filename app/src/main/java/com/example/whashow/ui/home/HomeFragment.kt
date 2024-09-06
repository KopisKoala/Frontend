package com.example.whashow.ui.home

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.whashow.MainActivity
import com.example.whashow.R
import com.example.whashow.base.BaseFragment
import com.example.whashow.data.Performance
import com.example.whashow.databinding.FragmentHomeBinding
import com.example.whashow.ui.home.homeAdapter.BannerAdapter
import com.example.whashow.ui.home.homeAdapter.DramaAdapter
import com.example.whashow.ui.home.homeAdapter.PopularAdapter
import com.example.whashow.ui.home.homeAdapter.RecommendHomeAdapter


class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var popularListAdapter: PopularAdapter
    private lateinit var recommendListAdapter: RecommendHomeAdapter
    private lateinit var bannerListAdapter: BannerAdapter
    private lateinit var dramaListAdapter: DramaAdapter

    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).binding.toolbar.setBackgroundColor(Color.WHITE)
        (activity as MainActivity).binding.btnBack.setImageResource(R.drawable.btn_back)
        (activity as MainActivity).binding.backTitle.setTextColor(Color.BLACK)
        (activity as MainActivity).ShowLogoAndSearch()

        homeViewModel.fetchPerformanceData()
        homeViewModel.fetchPopularMusicalsData()
        homeViewModel.fetchRecommendData()
        homeViewModel.fetchPopularDramaData()
    }

    override fun initDataBinding() {
        super.initDataBinding()
        (activity as MainActivity).binding.navigationMain.visibility = View.VISIBLE

        popularListAdapter = PopularAdapter(arrayListOf())
        popularListAdapter.setMyItemClickListener(object : PopularAdapter.MyItemClickListener {
            override fun onItemClick(banner: Performance) {
                val fragment = PerformanceDetailFragment().apply {
                    arguments = Bundle().apply {
                        putInt("perfId", banner.perfId)
                        putString("title", banner.title)
                    }
                }
                (activity as MainActivity).addFragment(fragment)
            }
        })

        dramaListAdapter = DramaAdapter(arrayListOf())
        dramaListAdapter.setMyItemClickListener(object : DramaAdapter.MyItemClickListener {
            override fun onItemClick(banner: Performance) {
                val fragment = PerformanceDetailFragment().apply {
                    arguments = Bundle().apply {
                        putInt("perfId", banner.perfId)
                        putString("title", banner.title)
                    }
                }
                (activity as MainActivity).addFragment(fragment)
            }
        })


        recommendListAdapter = RecommendHomeAdapter(arrayListOf())
        recommendListAdapter.setMyItemClickListener(object : RecommendHomeAdapter.MyItemClickListener {
            override fun onItemClick(banner: Performance) {
                val fragment = PerformanceDetailFragment().apply {
                    arguments = Bundle().apply {
                        putInt("perfId", banner.perfId)
                        putString("title", banner.title)
                    }
                }
                (activity as MainActivity).addFragment(fragment)
            }
        })

        bannerListAdapter = BannerAdapter(arrayListOf())
        bannerListAdapter.setMyItemClickListener(object : BannerAdapter.MyItemClickListener {
            override fun onItemClick(banner: Performance) {
                val fragment = PerformanceDetailFragment().apply {
                    arguments = Bundle().apply {
                        putInt("perfId", banner.perfId)
                        putString("title", banner.title)
                    }
                }
                (activity as MainActivity).addFragment(fragment)
            }
        })

        binding.viewpagerBanner.apply {
            adapter = bannerListAdapter
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
        }

        binding.rcPopular.apply {
            adapter = popularListAdapter
        }

        binding.rcDrama.apply {
            adapter = dramaListAdapter
        }

        binding.rcCommend.apply {
            adapter = recommendListAdapter
        }

        homeViewModel.performanceList.observe(viewLifecycleOwner, Observer { performances ->
            bannerListAdapter.updatePerformances(performances)
        })

        homeViewModel.popularList.observe(viewLifecycleOwner, Observer { performances ->
            popularListAdapter.updatePerformances(performances)
        })

        homeViewModel.dramaList.observe(viewLifecycleOwner, Observer { performances ->
            dramaListAdapter.updatePerformances(performances)
        })

        homeViewModel.recommendList.observe(viewLifecycleOwner, Observer { performances ->
            recommendListAdapter.updatePerformances(performances)
        })

        homeViewModel.error.observe(viewLifecycleOwner, Observer { errorMessage ->
            println("Error: $errorMessage")
        })
    }



    override fun initAfterBinding() {
        super.initAfterBinding()
        (activity as MainActivity).binding.navigationMain.visibility = View.VISIBLE


        binding.viewpagerBanner.apply {
            adapter = bannerListAdapter
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            })
        }

        binding.rcPopular.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = popularListAdapter
        }

        binding.rcDrama.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = dramaListAdapter
        }

        binding.rcCommend.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = recommendListAdapter
        }

        binding.btnEtiquette.setOnClickListener {
            (activity as? MainActivity)?.addFragment(EtiquetteFragment())
        }
    }
}
