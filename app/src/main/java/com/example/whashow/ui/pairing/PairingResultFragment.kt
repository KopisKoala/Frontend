package com.example.whashow.ui.pairing

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.whashow.MainActivity
import com.example.whashow.R
import com.example.whashow.apiManager.ApiManager
import com.example.whashow.base.BaseFragment
import com.example.whashow.data.PairReview
import com.example.whashow.data.Review
import com.example.whashow.data.getNickname
import com.example.whashow.data.getNicknameRequest
import com.example.whashow.databinding.FragmentPairingResultBinding
import com.example.whashow.login.LocalDataSource
import com.example.whashow.ui.recommand.SortResultSpinnerAdapter
import com.example.whashow.ui.recommand.Tag
import com.example.whashow.ui.recommand.TagAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PairingResultFragment : BaseFragment<FragmentPairingResultBinding>(R.layout.fragment_pairing_result) {

    private lateinit var reviewAdapter: ReviewAdapter
    //리뷰
    val reviewList = arrayListOf<Review>()
    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).binding.backTitle.text="페어링 추천받기"
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

        // spinnerRecommandSpace를 레이아웃 파일에서 가져옴
        val spinnerSortResult: Spinner = binding.spinnerReviewSort
        spinnerSortResult.adapter= SortResultSpinnerAdapter(requireContext(),R.layout.item_spinner_sort_result,sortTextList)
        spinnerSortResult.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val value = spinnerSortResult.getItemAtPosition(p2).toString()
                val Call2: Call<PairReview> =
                    ApiManager.pairingService.getInfo(
                        "Bearer " + LocalDataSource.getAccessToken()!!, 1,sortList[p2]
                    )
                // 비동기적으로 요청 수행
                Call2.enqueue(object : Callback<PairReview> {
                    override fun onResponse(
                        call: Call<PairReview>,
                        response: Response<PairReview>
                    ) {
                        if (response.isSuccessful) {
                            val data = response.body()?.result
                            if (data!=null){
                                binding.ratingBar.rating=data.averageRating
                                if (data.hashtags[0]=="null"){
                                    binding.hashtag1.visibility=View.GONE
                                    binding.hashtag2.visibility=View.GONE
                                }
                                else if (data.hashtags[1]=="null"){
                                    binding.hashtag1.text="#"+data.hashtags[0]
                                    binding.hashtag2.visibility=View.GONE
                                }
                                else {
                                    binding.hashtag1.text="#"+data.hashtags[0]
                                    binding.hashtag2.text="#"+data.hashtags[1]
                                }
                                reviewAdapter.reviewList=data.reviewList as ArrayList<Review>
                                reviewAdapter.notifyDataSetChanged()
                            }

                            Log.d("리뷰 목록 조회", data.toString())
                            Log.d("리뷰 목록 조회 서버", response.body()?.result.toString())

                        } else {
                            // 서버에서 오류 응답을 받은 경우 처리
                            Log.d("리뷰 목록 조회 서버", response.toString())
                        }

                    }

                    override fun onFailure(call: Call<PairReview>, t: Throwable) {
                        // 통신 실패 처리
                        Log.d("리뷰 목록 조회 서버", t.message.toString())
                    }

                })
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                // 선택되지 않은 경우
                val Call2: Call<PairReview> =
                    ApiManager.pairingService.getInfo(
                        "Bearer " + LocalDataSource.getAccessToken()!!, 1,sortList[0]
                    )
                // 비동기적으로 요청 수행
                Call2.enqueue(object : Callback<PairReview> {
                    override fun onResponse(
                        call: Call<PairReview>,
                        response: Response<PairReview>
                    ) {
                        if (response.isSuccessful) {
                            val data = response.body()?.result
                            if (data!=null){
                                binding.ratingBar.rating=data.averageRating
                                if (data.hashtags[0]=="null"){
                                    binding.hashtag1.visibility=View.GONE
                                    binding.hashtag2.visibility=View.GONE
                                }
                                else if (data.hashtags[1]=="null"){
                                    binding.hashtag1.text="#"+data.hashtags[0]
                                    binding.hashtag2.visibility=View.GONE
                                }
                                else {
                                    binding.hashtag1.text="#"+data.hashtags[0]
                                    binding.hashtag2.text="#"+data.hashtags[1]
                                }
                                reviewAdapter.reviewList=data.reviewList as ArrayList<Review>
                                reviewAdapter.notifyDataSetChanged()
                            }
                            Log.d("리뷰 목록 조회", data.toString())
                            Log.d("리뷰 목록 조회 서버", response.body()?.result.toString())

                        } else {
                            // 서버에서 오류 응답을 받은 경우 처리
                            Log.d("리뷰 목록 조회 서버", response.toString())
                        }

                    }

                    override fun onFailure(call: Call<PairReview>, t: Throwable) {
                        // 통신 실패 처리
                        Log.d("리뷰 목록 조회 서버", t.message.toString())
                    }

                })
            }
        }

        //리뷰
        /*val reviewList = arrayListOf(
            Review(R.drawable.img_profile, "뮤덕 84", 3.5F,"짜릿하다","옥주현 배우와 정선아 배우의 연기가 너무 좋았어요. 다음에 위키드를 관람한다면 두 배우의 호흡을 보고 싶어요!",true,"5" ),
            Review(R.drawable.img_profile, "금연이", 5F,"소름돋는다","무대를 찢어버리셨다. 역시 손승연 정선아였다.",false,"13" )
        )*/


        reviewAdapter = ReviewAdapter(reviewList)
        binding.reviewRv.adapter = reviewAdapter
        binding.reviewRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)



    }

    override fun initAfterBinding() {
        super.initAfterBinding()

    }
}