package com.example.whashow.ui.pairing

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import com.example.whashow.MainActivity
import com.example.whashow.R
import com.example.whashow.base.BaseFragment
import com.example.whashow.databinding.FragmentActorSearchBinding
import com.example.whashow.databinding.FragmentPerformanceResultBinding

class PerformanceResultFragment : BaseFragment<FragmentPerformanceResultBinding>(R.layout.fragment_performance_result) {

    private lateinit var performanceListManager: GridLayoutManager
    private lateinit var performanceListAdapter: PerformanceAdapterGrid
    private lateinit var selectBottomSheet: SelectBottomSheet
    private val modal = SelectBottomSheet()
    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).binding.backTitle.text="페어링 검색"
        (activity as MainActivity).ShowBackandTitle()
        (activity as MainActivity).binding.navigationMain.visibility=View.GONE
        binding.search.queryHint="위키드"
    }

    override fun initDataBinding() {
        super.initDataBinding()
        binding.search.isSubmitButtonEnabled = true
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                if (query=="손승연"){
                    (activity as MainActivity).addFragment(ActorSearchFragment())
                }
                if (query=="위키드"){
                    (activity as MainActivity).addFragment(PerformanceResultFragment())
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                return true
            }
        })

        val performanceList = arrayListOf(
            Performance(R.drawable.img_actor1,R.drawable.img_actor2,"옥주현 정선아", "위키드", "13", arrayListOf(
                Hashtag("웅장한"), Hashtag("멋있는")
            ) ),
            Performance(R.drawable.img_actor3,R.drawable.img_actor2,"손승연 정선아", "위키드", "25", arrayListOf(Hashtag("환상의 하모니")) )
        )

        performanceListManager = GridLayoutManager(requireContext(), 2)
        performanceListAdapter = PerformanceAdapterGrid(performanceList)

        val actorRecyclerList = binding.performanceRv.apply {
            setHasFixedSize(true)
            layoutManager = performanceListManager
            adapter = performanceListAdapter
        }

        binding.recommandResult.setOnClickListener {

            modal.show(parentFragmentManager, SelectBottomSheet.TAG)
        }

    }

    override fun initAfterBinding() {
        super.initAfterBinding()

    }
}