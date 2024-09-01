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
import com.example.whashow.data.PerformanceResultDTOList
import com.example.whashow.data.PerformanceReview
import com.example.whashow.databinding.FragmentRecommandResultBinding
import com.example.whashow.login.LocalDataSource
import com.example.whashow.viewModel.RecommandResultViewModel
import com.example.whashow.viewModel.RecommandViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RecommandResultFragment : BaseFragment<FragmentRecommandResultBinding>(R.layout.fragment_recommand_result) {

    private lateinit var reviewAdapter: RecommandAdapter
    private lateinit var performanceViewPager: PerformanceViewPagerAdapter
    private val recommandResultViewModel:RecommandResultViewModel by activityViewModels()
    private val recommandViewModel: RecommandViewModel by activityViewModels()
    //리뷰
    val reviewList = arrayListOf<PerformanceResultDTOList>()

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

        reviewAdapter = RecommandAdapter(arrayListOf())
        binding.reviewRv.adapter = reviewAdapter
        binding.reviewRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        performanceViewPager = PerformanceViewPagerAdapter(arrayListOf()) // 어댑터 생성
        binding.vp.adapter=performanceViewPager
        binding.vp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        recommandViewModel.recommandResultList.observe(viewLifecycleOwner) { list ->
            Log.d("공연 목록 조회", list.toString())
            performanceViewPager.updatePerformance(list)
            performanceViewPager.notifyDataSetChanged()
        }

        //페어 아이디 저장
        if (performanceViewPager.performanceList.isNotEmpty() && performanceViewPager.performanceList[0].performancesByStandard.isNotEmpty()){
            recommandResultViewModel.setPairId(performanceViewPager.performanceList[0].performancesByStandard[0].id)
        }
        else{
            reviewAdapter.updateReviews(arrayListOf())
            binding.reviewNum.text = "0"
        }


        // spinnerRecommandSpace를 레이아웃 파일에서 가져옴
        val spinnerSortResult: Spinner = binding.spinnerReviewSort
        spinnerSortResult.adapter= SortResultSpinnerAdapter(requireContext(),R.layout.item_spinner_sort_result,sortTextList)
        spinnerSortResult.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                recommandResultViewModel.pairId.observe(viewLifecycleOwner) { pairId ->
                    if (pairId != null) {
                        Log.d("리뷰 목록 조회", id.toString())
                        //정렬
                        val sortList = listOf(
                            "recent",
                            "like",
                            "asc",
                            "desc"
                        )
                        val Call2: Call<PerformanceReview> =
                            ApiManager.performanceService.getReview(
                                "Bearer " + LocalDataSource.getAccessToken()!!, pairId,sortList[p2]
                            )
                        // 비동기적으로 요청 수행
                        Call2.enqueue(object : Callback<PerformanceReview> {
                            override fun onResponse(
                                call: Call<PerformanceReview>,
                                response: Response<PerformanceReview>
                            ) {
                                if (response.isSuccessful) {
                                    if (isAdded && view != null) { // Fragment가 추가되었고, View가 유효한지 확인
                                        val data = response.body()?.result
                                        if (data != null) {
                                            recommandResultViewModel.setReviewList(data.reviewList)
                                            recommandResultViewModel.reviewList.observe(
                                                viewLifecycleOwner, // 안전하게 접근
                                                Observer { list ->
                                                    Log.d("리뷰 목록 조회", list.toString())
                                                    reviewAdapter.updatePairId(pairId)
                                                    reviewAdapter.updateReviews(list)
                                                    binding.reviewNum.text = list.size.toString()
                                                }
                                            )
                                        }
                                    } else {
                                        Log.d("Error", "Fragment view is not available")
                                    }
                                } else {
                                    Log.d("공연 리뷰 목록 조회 서버", response.toString())
                                }
                            }

                            override fun onFailure(call: Call<PerformanceReview>, t: Throwable) {
                                Log.d("공연 리뷰 목록 조회 서버", t.message.toString())
                            }
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
                        //정렬
                        val sortList = listOf(
                            "recent",
                            "like",
                            "asc",
                            "desc"
                        )
                        val Call2: Call<PerformanceReview> =
                            ApiManager.performanceService.getReview(
                                "Bearer " + LocalDataSource.getAccessToken()!!, pairId,sortList[0]
                            )
                        // 비동기적으로 요청 수행
                        Call2.enqueue(object : Callback<PerformanceReview> {
                            override fun onResponse(
                                call: Call<PerformanceReview>,
                                response: Response<PerformanceReview>
                            ) {
                                if (response.isSuccessful) {
                                    val data = response.body()?.result
                                    if (data != null) {
                                        recommandResultViewModel.setReviewList(data.reviewList)
                                        recommandResultViewModel.reviewList.observe(
                                            viewLifecycleOwner,
                                            Observer { list ->

                                                Log.d("리뷰 목록 조회", list.toString())
                                                reviewAdapter.updatePairId(pairId)
                                                reviewAdapter.updateReviews(list)
                                                binding.reviewNum.text = list.size.toString()

                                            })
                                    }

                                    Log.d("공연 리뷰 목록 조회", data.toString())
                                    Log.d("공연 리뷰 목록 조회 서버", response.body()?.result.toString())

                                } else {
                                    // 서버에서 오류 응답을 받은 경우 처리
                                    Log.d("공연 리뷰 목록 조회 서버", response.toString())
                                }

                            }

                            override fun onFailure(call: Call<PerformanceReview>, t: Throwable) {
                                // 통신 실패 처리
                                Log.d("공연 리뷰 목록 조회 서버", t.message.toString())
                            }

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
                recommandViewModel.recommandResultList.observe(viewLifecycleOwner) { list ->
                    Log.d("공연 목록 조회", list.toString())
                    performanceViewPager.updatePerformance(list)
                    performanceViewPager.notifyDataSetChanged()

                    if (position >= 0 && position < performanceViewPager.performanceList.size) {
                        val performancesByStandardList = performanceViewPager.performanceList[position].performancesByStandard

                        if (performancesByStandardList.isNotEmpty()) {
                            recommandResultViewModel.setPairId(performancesByStandardList[0].id)
                        } else {
                            Log.d("Error", "performancesByStandard list is empty at position $position.")
                            // 예외 처리 코드 추가 (예: 기본 ID 설정 또는 경고 메시지 표시)
                        }
                    } else {
                        Log.d("Error", "Invalid position: $position. performanceList size: ${performanceViewPager.performanceList.size}.")
                        // 예외 처리 코드 추가 (예: 기본 ID 설정 또는 경고 메시지 표시)
                    }
                }

                /*reviewAdapter.updateReviews(arrayListOf())
                binding.reviewNum.text = "0"*/
            }
        })




    }

    override fun initAfterBinding() {
        super.initAfterBinding()


    }
}


