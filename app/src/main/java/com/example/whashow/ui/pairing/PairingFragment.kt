package com.example.whashow.ui.pairing

import android.graphics.Color
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.whashow.MainActivity
import com.example.whashow.R
import com.example.whashow.base.BaseFragment
import com.example.whashow.databinding.FragmentPairingBinding
import com.example.whashow.ui.home.SearchViewModel

class PairingFragment : BaseFragment<FragmentPairingBinding>(R.layout.fragment_pairing) {
    private val searchViewModel : SearchViewModel by viewModels()

    private lateinit var pairListManager: GridLayoutManager
    private lateinit var pairListAdapter: RecentAdapter

    private lateinit var searchPairListManager: GridLayoutManager
    private lateinit var searchPairListAdapter: SearchPairAdapter

    override fun initStartView() {
        super.initStartView()
        //배경 흰색
        (activity as MainActivity).binding.toolbar.setBackgroundColor(Color.WHITE)
        (activity as MainActivity).binding.btnBack.setImageResource(R.drawable.btn_back)
        (activity as MainActivity).binding.backTitle.setTextColor(Color.BLACK)

        (activity as MainActivity).binding.mainTitle.text = "페어링 검색"
        (activity as MainActivity).ShowTitle()
        (activity as MainActivity).binding.navigationMain.visibility = View.VISIBLE
    }

    override fun initDataBinding() {
        super.initDataBinding()

        binding.search.isSubmitButtonEnabled = true
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    searchViewModel.fetchSearchPair(query)

                    searchViewModel.searchPairList.observe(viewLifecycleOwner, Observer { pairDetailListResDtoList ->

                        val pairDetailResDtoList = pairDetailListResDtoList.flatMap { it.pairDetailResDtoList }
                        searchPairListAdapter.updatePairs(pairDetailResDtoList)
                        binding.pairingRank.visibility = View.GONE
                        binding.pairingResult.visibility = View.VISIBLE
                        binding.pairCnt.text = pairDetailResDtoList.size.toString()
                    })

                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    searchViewModel.fetchPopularPair()
                    binding.pairingResult.visibility = View.GONE
                    binding.pairingRank.visibility = View.VISIBLE
                }
                return true
            }
        })
        //네비바 생기는거 방지
        binding.search.setOnCloseListener(object : SearchView.OnCloseListener {
            override fun onClose(): Boolean {
                (activity as MainActivity).binding.navigationMain.visibility = View.VISIBLE
                return true
            }
        })
        binding.search.setOnClickListener {
            (activity as MainActivity).binding.navigationMain.visibility = View.GONE
        }

        pairListManager = GridLayoutManager(requireContext(), 2)
        pairListAdapter = RecentAdapter(arrayListOf())

        searchPairListManager = GridLayoutManager(requireContext(), 2)
        searchPairListAdapter = SearchPairAdapter(arrayListOf())

        searchViewModel.fetchPopularPair()
        searchViewModel.popularPairList.observe(viewLifecycleOwner, Observer { pairList ->
            pairListAdapter.updatePairs(pairList)
        })

        val actorRecyclerList = binding.pairRv.apply {
            setHasFixedSize(true)
            layoutManager = pairListManager
            adapter = pairListAdapter
        }
        val searchRecyclerList = binding.performanceRv.apply {
            setHasFixedSize(true)
            layoutManager = searchPairListManager
            adapter = searchPairListAdapter
        }
        /*recentListAdapter = RecentAdapter(recentList)
        binding.pairRv.adapter = recentListAdapter
        binding.pairRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)


        popularpairListAdapter.setMyItemClickListener(object : PopularPairAdapter.MyItemClickListener {
            override fun onDeleteClick(position: Int) {
                popularpairListAdapter.notifyDataSetChanged()
            }
        })*/
    }

    override fun initAfterBinding() {
        super.initAfterBinding()

    }
}