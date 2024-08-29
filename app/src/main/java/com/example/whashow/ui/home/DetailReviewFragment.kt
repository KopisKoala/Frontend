package com.example.whashow.ui.home

import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.whashow.MainActivity
import com.example.whashow.R
import com.example.whashow.base.BaseFragment
import com.example.whashow.databinding.FragmentDetailReviewBinding
import com.example.whashow.ui.home.Adapter.PerformanceReviewAdapter
import com.example.whashow.ui.recommand.SortResultSpinnerAdapter

class DetailReviewFragment: BaseFragment<FragmentDetailReviewBinding>(R.layout.fragment_detail_review) {

    private val viewModel: PerformanceDetailViewModel by viewModels()
    //private var perfId: Int? = null
    private val _perfId= MutableLiveData<Int>()
    val perId: LiveData<Int>
        get()=_perfId

    private lateinit var reviewAdapter: PerformanceReviewAdapter

    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).binding.navigationMain.visibility = View.GONE
        _perfId.value = arguments?.getInt("perfId")
        _perfId.value?.let {
            Log.d("리뷰 목록 조회",it.toString())
            viewModel.fetchPerformanceData(it)
        }
    }
    override fun initDataBinding() {
        super.initDataBinding()
        (activity as MainActivity).binding.navigationMain.visibility = View.GONE


        _perfId.observe(requireActivity(), Observer { id ->
            reviewAdapter = PerformanceReviewAdapter(arrayListOf(), id)
        })

        binding.reviewRv.adapter = reviewAdapter
        binding.reviewRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

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


        val sortTextList = listOf(
            "최신 순",
            "좋아요 순",
            "별점 낮은 순",
            "별점 높은 순"
        )
        // spinnerRecommandSpace를 레이아웃 파일에서 가져옴
        val spinnerSortResult: Spinner = binding.spinnerReviewSort
        spinnerSortResult.adapter= SortResultSpinnerAdapter(requireContext(),R.layout.item_spinner_sort_result,sortTextList)
        spinnerSortResult.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val value = spinnerSortResult.getItemAtPosition(p2).toString()
                _perfId.observe(requireActivity(), Observer { id ->
                    Log.d("리뷰 목록 조회", id.toString())
                    viewModel.fetchPerformanceReview(id, p2)
                    viewModel.reviewList.observe(viewLifecycleOwner, Observer { list ->
                        reviewAdapter.updateReviews(list)
                        binding.reviewNum.text=list.size.toString()
                    })
                })

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }



    }
    override fun initAfterBinding() {
        super.initAfterBinding()
        (activity as MainActivity).binding.navigationMain.visibility = View.GONE
    }
}
