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
import androidx.viewpager2.widget.ViewPager2
import com.example.whashow.MainActivity
import com.example.whashow.R
import com.example.whashow.apiManager.ApiManager
import com.example.whashow.base.BaseFragment
import com.example.whashow.data.PairReview
import com.example.whashow.data.PairReviewResult
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
    private lateinit var pairViewPager: PairingViewPagerAdapter
    //리뷰
    val reviewList = arrayListOf<Review>()
    val pairingList = arrayListOf(
        PairReviewResult(
            averageRating = 4.5f,
            hashtags = listOf("맛있다", "신선하다"),
            reviewCount = 120,
            reviewList = arrayListOf(
                Review(
                    content = "정말 신선하고 맛있어요! 재구매 의사 100%!",
                    hashTag = "#맛있다 #신선하다",
                    id = 1,
                    isWriter = true,
                    likeCount = 15,
                    rating = 5,
                    writer = "user1",
                    writerProfileImage = "https://example.com/images/user1.png"
                ),
                Review(
                    content = "가격 대비 괜찮은 선택이었습니다.",
                    hashTag = "#가성비 #만족",
                    id = 2,
                    isWriter = false,
                    likeCount = 10,
                    rating = 4,
                    writer = "user2",
                    writerProfileImage = "https://example.com/images/user2.png"
                )
            )
        ),
        PairReviewResult(
            averageRating = 3.8f,
            hashtags = listOf("보통", "괜찮다"),
            reviewCount = 85,
            reviewList = arrayListOf(
                Review(
                    content = "평범하지만 나쁘지 않아요.",
                    hashTag = "#보통 #무난",
                    id = 3,
                    isWriter = false,
                    likeCount = 7,
                    rating = 3,
                    writer = "user3",
                    writerProfileImage = "https://example.com/images/user3.png"
                ),
                Review(
                    content = "기대보다 많이 부족했어요.",
                    hashTag = "#실망 #별로",
                    id = 4,
                    isWriter = true,
                    likeCount = 3,
                    rating = 2,
                    writer = "user4",
                    writerProfileImage = "https://example.com/images/user4.png"
                )
            )
        ),
        PairReviewResult(
            averageRating = 4.9f,
            hashtags = listOf("강력추천", "최고"),
            reviewCount = 200,
            reviewList = arrayListOf(
                Review(
                    content = "품질이 좋고 기대 이상이었어요!",
                    hashTag = "#추천 #만족",
                    id = 6,
                    isWriter = false,
                    likeCount = 20,
                    rating = 5,
                    writer = "user6",
                    writerProfileImage = "https://example.com/images/user6.png"
                ),
                Review(
                    content = "그럭저럭 괜찮았어요.",
                    hashTag = "#보통 #무난",
                    id = 7,
                    isWriter = false,
                    likeCount = 5,
                    rating = 3,
                    writer = "user7",
                    writerProfileImage = "https://example.com/images/user7.png"
                ),
                Review(
                    content = "맛있긴 한데 조금 비싼 것 같아요.",
                    hashTag = "#맛있다 #비싸다",
                    id = 8,
                    isWriter = true,
                    likeCount = 8,
                    rating = 4,
                    writer = "user8",
                    writerProfileImage = "https://example.com/images/user8.png"
                )
            )
        )
    )
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
                            if (data != null) {

                                reviewAdapter.reviewList = data.reviewList as ArrayList<Review>
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

        binding.vp.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                // position에 해당하는 데이터를 가져와서 리스트를 갱신
                val selectedPairReview = pairingList[position].reviewList
                Log.d("리뷰 목록 조회 서버", position.toString())
                // reviewList를 새 데이터로 갱신
                reviewAdapter.reviewList = selectedPairReview as ArrayList<Review>
                reviewAdapter.notifyDataSetChanged()

                // 다른 필요한 갱신 작업이 있으면 여기에 추가
            }
        })

        reviewAdapter = ReviewAdapter(reviewList)
        binding.reviewRv.adapter = reviewAdapter
        binding.reviewRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        pairViewPager = PairingViewPagerAdapter(pairingList) // 어댑터 생성
        binding.vp.adapter=pairViewPager
        binding.vp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

    }

    override fun initAfterBinding() {
        super.initAfterBinding()

    }
}