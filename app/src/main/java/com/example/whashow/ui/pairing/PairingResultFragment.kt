package com.example.whashow.ui.pairing

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.whashow.MainActivity
import com.example.whashow.R
import com.example.whashow.base.BaseFragment
import com.example.whashow.databinding.FragmentPairingResultBinding
import com.example.whashow.ui.recommand.SortResultSpinnerAdapter
import com.example.whashow.ui.recommand.TagAdapter

class PairingResultFragment : BaseFragment<FragmentPairingResultBinding>(R.layout.fragment_pairing_result) {

    //private lateinit var tagListAdapter: TagAdapter
    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).binding.backTitle.text="페어링 추천받기"
        (activity as MainActivity).ShowBackandTitle()
        (activity as MainActivity).binding.navigationMain.visibility=View.GONE
    }

    override fun initDataBinding() {
        super.initDataBinding()

        //정렬
        val resultList = listOf(
            "최신 순",
            "좋아요 순",
            "별점 낮은 순",
            "별점 높은 순"
        )

        // spinnerRecommandSpace를 레이아웃 파일에서 가져옴
        val spinnerSortResult: Spinner = binding.spinnerReviewSort
        spinnerSortResult.adapter= SortResultSpinnerAdapter(requireContext(),R.layout.item_spinner_sort_result,resultList)
        spinnerSortResult.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val value = spinnerSortResult.getItemAtPosition(p2).toString()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                // 선택되지 않은 경우
            }
        }

        //리뷰
        /*tagListAdapter = TagAdapter(tagList)
        binding.tagRv.adapter = tagListAdapter
        binding.tagRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)*/

    }

    override fun initAfterBinding() {
        super.initAfterBinding()

    }
}