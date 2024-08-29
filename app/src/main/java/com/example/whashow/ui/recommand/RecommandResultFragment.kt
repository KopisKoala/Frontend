package com.example.whashow.ui.recommand

import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.whashow.MainActivity
import com.example.whashow.R
import com.example.whashow.apiManager.ApiManager
import com.example.whashow.base.BaseFragment
import com.example.whashow.data.PairReview
import com.example.whashow.data.PerformancesByStandard
import com.example.whashow.data.PerformancesByStandardList
import com.example.whashow.data.Review
import com.example.whashow.databinding.FragmentRecommandResultBinding
import com.example.whashow.login.LocalDataSource
import com.example.whashow.ui.pairing.ReviewAdapter
import com.example.whashow.viewModel.RecommandResultViewModel
import com.example.whashow.viewModel.RecommandViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RecommandResultFragment : BaseFragment<FragmentRecommandResultBinding>(R.layout.fragment_recommand_result) {

    private lateinit var reviewAdapter: ReviewAdapter
    private lateinit var performanceViewPager: PerformanceViewPagerAdapter
    private val recommandResultViewModel:RecommandResultViewModel by activityViewModels()
    private val recommandViewModel: RecommandViewModel by activityViewModels()
    //리뷰
    val reviewList = arrayListOf<Review>()

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
        //정렬
        val sortTextList = listOf(
            "최신 순",
            "좋아요 순",
            "별점 낮은 순",
            "별점 높은 순"
        )
        val sortList = listOf(
            "recent",
            "like",
            "asc",
            "desc"
        )

        performanceViewPager = PerformanceViewPagerAdapter(arrayListOf()) // 어댑터 생성
        binding.vp.adapter=performanceViewPager
        binding.vp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        performanceViewPager.performanceList=recommandViewModel.recommandResultList.value as ArrayList<PerformancesByStandard>
        if (performanceViewPager.performanceList.isNotEmpty() && performanceViewPager.performanceList[0].performancesByStandard.isNotEmpty()){
            recommandResultViewModel.setPairId(performanceViewPager.performanceList[0].performancesByStandard[0].id)
            performanceViewPager.notifyDataSetChanged()
        }
        reviewAdapter = ReviewAdapter(arrayListOf())
        binding.reviewRv.adapter = reviewAdapter
        binding.reviewRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        // spinnerRecommandSpace를 레이아웃 파일에서 가져옴
        val spinnerSortResult: Spinner = binding.spinnerReviewSort
        spinnerSortResult.adapter= SortResultSpinnerAdapter(requireContext(),R.layout.item_spinner_sort_result,sortTextList)
        spinnerSortResult.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                recommandResultViewModel.pairId.observe(viewLifecycleOwner) { pairId ->
                    if (pairId != null) {
                        Log.d("리뷰 목록 조회", id.toString())
                        recommandResultViewModel.fetchPerformanceReview(pairId!!, p2)
                        recommandResultViewModel.reviewList.observe(
                            viewLifecycleOwner,
                            Observer { list ->
                                reviewAdapter.updateReviews(list)
                                binding.reviewNum.text = list.size.toString()
                            })
                    }
                    else {
                        recommandResultViewModel.reviewList.observe(
                            viewLifecycleOwner,
                            Observer { list ->
                                reviewAdapter.updateReviews(list)
                                binding.reviewNum.text = list.size.toString()
                            })
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                recommandResultViewModel.pairId.observe(viewLifecycleOwner) { pairId ->
                    if (pairId != null) {
                        Log.d("리뷰 목록 조회", id.toString())
                        recommandResultViewModel.fetchPerformanceReview(pairId!!, 0)
                        recommandResultViewModel.reviewList.observe(
                            viewLifecycleOwner,
                            Observer { list ->
                                reviewAdapter.updateReviews(list)
                                binding.reviewNum.text = list.size.toString()
                            })
                    }
                    else {
                    recommandResultViewModel.reviewList.observe(
                        viewLifecycleOwner,
                        Observer { list ->
                            reviewAdapter.updateReviews(list)
                            binding.reviewNum.text = list.size.toString()
                        })
                }
                }

            }
        }

        binding.vp.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                performanceViewPager.performanceList=recommandViewModel.recommandResultList.value as ArrayList<PerformancesByStandard>
                recommandResultViewModel.setPairId(performanceViewPager.performanceList[position].performancesByStandard[0].id)
                performanceViewPager.notifyDataSetChanged()
            }
        })




    }

    override fun initAfterBinding() {
        super.initAfterBinding()


    }
}


