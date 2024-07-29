package com.example.whashow.ui.home

import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import com.example.whashow.MainActivity
import com.example.whashow.R
import com.example.whashow.base.BaseFragment
import com.example.whashow.databinding.FragmentSearchBinding
import com.example.whashow.ui.home.Adapter.PerformanceAdapter


class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    private lateinit var postListManager: GridLayoutManager
    private lateinit var postListAdapter: PerformanceAdapter


    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).NoShow()
        (activity as MainActivity).binding.mainTitle.text="공연 검색"
        (activity as MainActivity).binding.navigationMain.visibility=View.GONE
    }

    override fun initDataBinding() {
        super.initDataBinding()
        // 위젯 사용 시
        binding.search.isSubmitButtonEnabled = false
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.performanceTitle.text = "공연 검색 결과"
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        var postList = arrayListOf(
            Performance(
                R.drawable.img_recommand_post,"2020 신년 콘서트 VOICE OF THE MAS..", "인천문화예술회관 대공연장 (1.2km)", 3.5F,"(434)"
            ),
            Performance(R.drawable.img_recommand_post2,"예술 회복 지원 사업 뮤지컬 한여름밤의 꿈", "경기아트센터 소극장 (2.3km)", 2F,"(12)"
            ),
            Performance(
                R.drawable.img_recommand_post3,"서초 문화재단 SSUM 타는 콘서트", "경기아트센터 소극장 (2.3km)", 5F,"(35)"
            ),
            Performance(
                R.drawable.img_recommand_post4,"대한민국 대표 창작 뮤지컬 빨래", "동양예술극장 1관",4.5F,"(125)"
            ),
            Performance(
                R.drawable.img_recommand_post,"2020 신년 콘서트 VOICE OF THE MAS..", "인천문화예술회관 대공연장 (1.2km)", 3.5F,"(434)"
            ),
            Performance(R.drawable.img_recommand_post2,"예술 회복 지원 사업 뮤지컬 한여름밤의 꿈", "경기아트센터 소극장 (2.3km)", 2F,"(12)"
            ),
            Performance(
                R.drawable.img_recommand_post3,"서초 문화재단 SSUM 타는 콘서트", "경기아트센터 소극장 (2.3km)", 5F,"(35)"
            ),
            Performance(
                R.drawable.img_recommand_post4,"대한민국 대표 창작 뮤지컬 빨래", "동양예술극장 1관",4.5F,"(125)"
            )


        )

        postListManager = GridLayoutManager(requireContext(), 2)
        postListAdapter = PerformanceAdapter(postList)

        val postRecyclerList = binding.rcSearchedPerformance.apply {
            setHasFixedSize(true)
            layoutManager = postListManager
            adapter = postListAdapter
        }
    }

    override fun initAfterBinding() {
        super.initAfterBinding()
    }


}