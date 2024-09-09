package com.example.whashow.ui.pairing

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
import com.example.whashow.data.PairReviewResult
import com.example.whashow.data.Review
import com.example.whashow.databinding.FragmentPairingResultBinding
import com.example.whashow.login.LocalDataSource
import com.example.whashow.ui.pairing.pairingAdapter.PairingReviewAdapter
import com.example.whashow.ui.pairing.pairingAdapter.PairingViewPagerAdapter
import com.example.whashow.ui.performance.performanceAdapter.SortResultSpinnerAdapter
import com.example.whashow.viewModel.PairingViewModel
import com.example.whashow.viewModel.PerformanceViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PairingResultFragment : BaseFragment<FragmentPairingResultBinding>(R.layout.fragment_pairing_result) {

    private lateinit var pairingReviewAdapter: PairingReviewAdapter
    private lateinit var pairViewPager: PairingViewPagerAdapter

    private val pairingViewModel: PairingViewModel by activityViewModels()

    val sortTextList = listOf(
        "최신 순",
        "좋아요 순",
        "별점 낮은 순",
        "별점 높은 순"
    )

    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).binding.backTitle.text="페어링 추천받기"
        (activity as MainActivity).ShowBackandTitle()
        (activity as MainActivity).binding.navigationMain.visibility=View.GONE

        pairingViewModel.fetchPairingList()
        pairingViewModel.pairId.observe(viewLifecycleOwner) { id ->
            pairingViewModel.fetchPairingReview(id!!, 0)
        }
    }

    override fun initDataBinding() {
        super.initDataBinding()

        pairViewPager = PairingViewPagerAdapter(arrayListOf())
        binding.vp.adapter=pairViewPager
        binding.vp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        pairingReviewAdapter = PairingReviewAdapter(arrayListOf())
        binding.reviewRv.adapter = pairingReviewAdapter
        binding.reviewRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        binding.spinnerReviewSort.adapter= SortResultSpinnerAdapter(requireContext(),R.layout.item_spinner_sort_result,sortTextList)

        pairingViewModel.recommandResultList.observe(viewLifecycleOwner) { list ->
            pairViewPager.updatePair(list)
            pairViewPager.notifyDataSetChanged()

            if (list.isNotEmpty()) {

                // 기본 정렬 조건으로 첫 번째 항목(최신 순)에 대한 리뷰 데이터를 초기화 시에 바로 요청
                binding.spinnerReviewSort.setSelection(0)
                pairingViewModel.pairId.observe(viewLifecycleOwner) { Id ->
                    pairingViewModel.fetchPairingReview(Id!!, 0)
                }

                pairingViewModel.reviewList.observe(viewLifecycleOwner) { reviewlist ->
                    pairingReviewAdapter.updateReviews(reviewlist)
                    binding.reviewNum.text = reviewlist.size.toString()
                }

                binding.spinnerReviewSort.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            p0: AdapterView<*>?,
                            p1: View?,
                            p2: Int,
                            p3: Long
                        ) {
                            pairingViewModel.pairId.observe(viewLifecycleOwner) { Id ->

                                pairingViewModel.fetchPairingReview(Id!!, p2)
                                pairingViewModel.reviewList.observe(
                                    viewLifecycleOwner, // 안전하게 접근
                                    Observer { reviewlist ->
                                        Log.d("리뷰 목록 조회", list.toString())
                                        pairingReviewAdapter.updatePairId(
                                            Id
                                        )
                                        pairingReviewAdapter.updateReviews(
                                            reviewlist
                                        )
                                        binding.reviewNum.text =
                                            reviewlist.size.toString()
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
                            pairingViewModel.setPairId(
                                pairViewPager.pairingList[position].pairDetailResDtoByStandard.id
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
            }
        }

    }

    override fun initAfterBinding() {
        super.initAfterBinding()

    }
}