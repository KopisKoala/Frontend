package com.example.whashow.ui.performance

import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.whashow.MainActivity
import com.example.whashow.R
import com.example.whashow.base.BaseFragment
import com.example.whashow.data.PerformanceResultDTOList
import com.example.whashow.databinding.FragmentRecommandResultBinding
import com.example.whashow.ui.performance.performanceAdapter.PerformanceViewPagerAdapter
import com.example.whashow.ui.performance.performanceAdapter.PerformanceReviewAdapter
import com.example.whashow.ui.performance.performanceAdapter.SortResultSpinnerAdapter
import com.example.whashow.viewModel.PerformanceViewModel


class PerformanceResultFragment :
    BaseFragment<FragmentRecommandResultBinding>(R.layout.fragment_recommand_result) {

    private lateinit var performanceReviewAdapter: PerformanceReviewAdapter
    private lateinit var performanceViewPager: PerformanceViewPagerAdapter

    private val performanceViewModel: PerformanceViewModel by activityViewModels()

    //정렬
    private val sortTextList = listOf(
        "최신 순",
        "좋아요 순",
        "별점 낮은 순",
        "별점 높은 순"
    )

    //리뷰
    val reviewList = arrayListOf<PerformanceResultDTOList>()

    override fun initStartView() {
        super.initStartView()

        (activity as MainActivity).binding.toolbar.setBackgroundColor(Color.WHITE)
        (activity as MainActivity).binding.btnBack.setImageResource(R.drawable.btn_back)
        (activity as MainActivity).binding.backTitle.setTextColor(Color.BLACK)
        (activity as MainActivity).binding.backTitle.text = "공연 추천"
        (activity as MainActivity).ShowBackandTitle()

        (activity as MainActivity).binding.navigationMain.visibility = View.GONE
    }

    override fun initDataBinding() {
        super.initDataBinding()

        performanceViewPager = PerformanceViewPagerAdapter(arrayListOf()) // 어댑터 생성
        binding.vp.adapter = performanceViewPager
        binding.vp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        performanceReviewAdapter = PerformanceReviewAdapter(arrayListOf())
        binding.reviewRv.adapter = performanceReviewAdapter
        binding.reviewRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        binding.spinnerReviewSort.adapter = SortResultSpinnerAdapter(
            requireContext(),
            R.layout.item_spinner_sort_result,
            sortTextList
        )

        performanceViewModel.recommandResultList.observe(viewLifecycleOwner) { list ->
            performanceViewPager.updatePerformance(list)
            performanceViewPager.notifyDataSetChanged()

            //공연 아이디 저장
            if (performanceViewPager.performanceList.isNotEmpty()) {
                binding.spinnerReviewSort.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            p0: AdapterView<*>?,
                            p1: View?,
                            p2: Int,
                            p3: Long
                        ) {
                            performanceViewModel.performanceId.observe(viewLifecycleOwner) { Id ->

                                performanceViewModel.fetchPerformanceReview(Id!!, p2)
                                performanceViewModel.reviewList.observe(
                                    viewLifecycleOwner, // 안전하게 접근
                                    Observer { list ->
                                        Log.d("리뷰 목록 조회", list.toString())
                                        performanceReviewAdapter.updatePerformanceId(
                                            Id
                                        )
                                        performanceReviewAdapter.updateReviews(
                                            list
                                        )
                                        binding.reviewNum.text =
                                            list.size.toString()
                                    }
                                )

                            }
                        }

                        override fun onNothingSelected(p0: AdapterView<*>?) {

                        }
                    }

                binding.vp.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)

                        try {
                            performanceViewModel.setPerformanceId(
                                performanceViewPager.performanceList[position].performancesByStandard[0].id
                            )
                        }
                        catch (e: ArrayIndexOutOfBoundsException) {
                            e.printStackTrace()
                        }
                        catch (e: ArrayIndexOutOfBoundsException) {
                            e.printStackTrace()
                        }
                    }

                })

            } else {
                performanceReviewAdapter.updateReviews(arrayListOf())
                binding.reviewNum.text = "0"
            }

        }


    }

    override fun initAfterBinding() {
        super.initAfterBinding()


    }
}


