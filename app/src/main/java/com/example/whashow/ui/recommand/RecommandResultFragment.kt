package com.example.whashow.ui.recommand

import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.whashow.MainActivity
import com.example.whashow.R
import com.example.whashow.base.BaseFragment
import com.example.whashow.databinding.FragmentRecommandResultBinding
import java.lang.Float

class RecommandResultFragment : BaseFragment<FragmentRecommandResultBinding>(R.layout.fragment_recommand_result) {

    private lateinit var postListManager: GridLayoutManager
    private lateinit var postListAdapter: RecommandAdapterGrid
    private lateinit var tagListAdapter: TagAdapter
    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).binding.toolbar.setBackgroundColor(Color.WHITE)
        (activity as MainActivity).binding.btnBack.setImageResource(R.drawable.btn_back)
        (activity as MainActivity).binding.backTitle.setTextColor(Color.BLACK)
        (activity as MainActivity).binding.backTitle.text="공연 추천"
        (activity as MainActivity).ShowBackandTitle()
        (activity as MainActivity).binding.navigationMain.visibility=View.GONE
    }

    override fun initDataBinding() {
        super.initDataBinding()

        binding.search.isSubmitButtonEnabled = true
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                return true
            }
        })

        var postList = arrayListOf(
            RecommandResult(
                R.drawable.img_recommand_post,"2020 신년 콘서트 VOICE OF THE MAS..", "인천문화예술회관 대공연장 (1.2km)", 3.5F,"(434)"
            ),
            RecommandResult(R.drawable.img_recommand_post2,"예술 회복 지원 사업 뮤지컬 한여름밤의 꿈", "경기아트센터 소극장 (2.3km)", 2F,"(12)"
            ),
            RecommandResult(
                R.drawable.img_recommand_post3,"서초 문화재단 SSUM 타는 콘서트", "경기아트센터 소극장 (2.3km)", 5F,"(35)"
            ),
            RecommandResult(
                R.drawable.img_recommand_post4,"대한민국 대표 창작 뮤지컬 빨래", "동양예술극장 1관",4.5F,"(125)"
            )
        )

        val tagList = arrayListOf(
            Tag("인천문화예술회관"),
            Tag("경기아트센터소극장"),
            Tag("반포심산아트홀"),
        )

        postListManager = GridLayoutManager(requireContext(), 2)
        postListAdapter = RecommandAdapterGrid(postList)

        val postRecyclerList = binding.recommandRv.apply {
            setHasFixedSize(true)
            layoutManager = postListManager
            adapter = postListAdapter
        }

        tagListAdapter = TagAdapter(tagList)
        binding.tagRv.adapter = tagListAdapter
        binding.tagRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        tagListAdapter.setMyItemClickListener(object:TagAdapter.MyItemClickListener{
            override fun onDeleteClick(position: Int) {
                tagListAdapter.removeItem(position)
                tagListAdapter.notifyDataSetChanged()
            }
        })

        //정렬
        val resultList = listOf(
            "인기 순",
            "거리 순"
        )

        // spinnerRecommandSpace를 레이아웃 파일에서 가져옴
        val spinnerSortResult: Spinner = binding.spinnerSortResult
        spinnerSortResult.adapter= SortResultSpinnerAdapter(requireContext(),R.layout.item_spinner_sort_result,resultList)
        spinnerSortResult.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val value = spinnerSortResult.getItemAtPosition(p2).toString()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                // 선택되지 않은 경우
            }
        }
    }

    override fun initAfterBinding() {
        super.initAfterBinding()


    }
}


