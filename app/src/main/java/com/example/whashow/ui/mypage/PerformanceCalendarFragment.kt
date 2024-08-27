package com.example.whashow.ui.mypage

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.bumptech.glide.Glide
import com.example.whashow.R
import com.example.whashow.apiManager.ApiManager
import com.example.whashow.base.BaseFragment
import com.example.whashow.data.AddMemo
import com.example.whashow.data.CalendarDayReview
import com.example.whashow.data.MemoRequestBody
import com.example.whashow.data.Partner
import com.example.whashow.data.ReviewX
import com.example.whashow.databinding.FragmentPerformanceCalendarBinding
import com.example.whashow.login.LocalDataSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PerformanceCalendarFragment : BaseFragment<FragmentPerformanceCalendarBinding>(R.layout.fragment_performance_calendar) {

    private lateinit var monthAdapter:MonthAdapter
    var position:Int=Int.MAX_VALUE/2
    private var partner :Int=0
    private var reviewId:String=""
    override fun initStartView() {
        super.initStartView()
    }

    val reviewList = arrayListOf(
        ReviewX(id = 1, performanceDate = "4", poster = R.drawable.img_poster_small.toString()),
        ReviewX(id = 2, performanceDate = "22", poster = R.drawable.img_poster_small2.toString()),
        ReviewX(id = 3, performanceDate = "26", poster = R.drawable.img_poster_small3.toString())
    )

    override fun initDataBinding() {
        super.initDataBinding()

        monthAdapter=MonthAdapter(reviewList)
        monthAdapter.setMyItemClickListener(object:MonthAdapter.MyItemClickListener{
            override fun onBtnClick(id: String) {

                Log.d("일 목록 조회", id)
                binding.calendarDetail.visibility = View.VISIBLE
                val call: Call<CalendarDayReview> = ApiManager.mypageService.getDayReview(
                    "Bearer " + LocalDataSource.getAccessToken(), id
                )

                call.enqueue(object : Callback<CalendarDayReview> {
                    @SuppressLint("SetTextI18n")
                    override fun onResponse(
                        call: Call<CalendarDayReview>,
                        response: Response<CalendarDayReview>
                    ) {
                        if (response.isSuccessful) {
                            val data = response.body()?.result
                            if (data != null) {
                                Glide.with(binding.imgPoster.context)
                                    .load(data.poster)
                                    .override(1500, 1500)
                                    .placeholder(R.drawable.img_poster_small)
                                    .error(R.drawable.img_poster_small)
                                    .into(binding.imgPoster)

                                binding.paringFeature.text = "#" + data.hashtag
                                binding.performanceDate.text = data.performanceDate
                                binding.posterTitle.text = data.performanceName
                                binding.genre.text = if (data.performanceType == "MUSICAL") "뮤지컬" else "연극"
                                binding.pairRating.rating = data.pairRatings.toFloat()
                                binding.performanceRating.rating = data.performanceRatings.toFloat()

                                when (data.viewingPartner) {
                                    "FAMILY" -> setPartnerSelection(binding.icFamily, binding.tagText)
                                    "FRIEND" -> setPartnerSelection(binding.icSmile, binding.tagText2)
                                    "COUPLE" -> setPartnerSelection(binding.icHeart, binding.tagText3)
                                    "ALONE" -> setPartnerSelection(binding.icFamily, binding.tagText4)
                                }
                            }
                            Log.d("일 목록 조회", data.toString())
                            Log.d("일 목록 조회 서버", response.body()?.result.toString())
                        } else {
                            Log.d("일 목록 조회 서버", response.toString())
                        }
                    }

                    override fun onFailure(call: Call<CalendarDayReview>, t: Throwable) {
                        Log.d("일 목록 조회 서버", t.message.toString())
                    }
                })

                val call2: Call<Partner> = ApiManager.mypageService.getPartner(
                    "Bearer " + LocalDataSource.getAccessToken(), id, partner
                )

                call2.enqueue(object : Callback<Partner> {
                    override fun onResponse(call: Call<Partner>, response: Response<Partner>) {
                        if (response.isSuccessful) {
                            val data = response.body()?.result
                            Log.d("함께 조회", data.toString())
                            Log.d("함께 서버", response.body()?.result.toString())
                        } else {
                            Log.d("함께 서버", response.toString())
                        }
                    }

                    override fun onFailure(call: Call<Partner>, t: Throwable) {
                        Log.d("함께 서버", t.message.toString())
                    }
                })

            }

            })

        //함꼐 본 사람 수정
        binding.tagText.setOnClickListener {
            /*binding.tagText.isSelected=!binding.tagText.isSelected
            binding.icFamily.isSelected=!binding.icFamily.isSelected*/
            partner= 1
        }
        binding.tagText2.setOnClickListener {
            /*binding.tagText2.isSelected=!binding.tagText2.isSelected
            binding.icSmile.isSelected=!binding.icSmile.isSelected*/
            partner = 2
        }
        binding.tagText3.setOnClickListener {
            /*binding.tagText3.isSelected=!binding.tagText3.isSelected
            binding.icHeart.isSelected=!binding.icHeart.isSelected*/
            partner = 3
        }
        binding.tagText4.setOnClickListener {
            /*binding.tagText4.isSelected=!binding.tagText4.isSelected
            binding.icFamily.isSelected=!binding.icFamily.isSelected*/
            partner = 4
        }

        binding.calRecycler.layoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        binding.calRecycler.adapter=monthAdapter
        binding.calRecycler.scrollToPosition(position)
        val snap=PagerSnapHelper()
        snap.attachToRecyclerView(binding.calRecycler)

        //리뷰
        binding.plus1.setOnClickListener {
            binding.plus1.visibility=View.GONE
            binding.togle1.visibility=View.VISIBLE
            binding.reviewDetail.visibility=View.VISIBLE
        }
        binding.togle1.setOnClickListener {
            binding.togle1.visibility=View.GONE
            binding.plus1.visibility=View.VISIBLE
            binding.reviewDetail.visibility=View.GONE
        }

        //함께 본사람
        binding.plus2.setOnClickListener {
            binding.plus2.visibility=View.GONE
            binding.togle2.visibility=View.VISIBLE
            binding.peopleDetail.visibility=View.VISIBLE
        }
        binding.togle2.setOnClickListener {
            binding.togle2.visibility=View.GONE
            binding.plus2.visibility=View.VISIBLE
            binding.peopleDetail.visibility=View.GONE
        }

        //메모
        binding.plus3.setOnClickListener {
            binding.plus3.visibility=View.GONE
            binding.togle3.visibility=View.VISIBLE
            binding.memoDetail.visibility=View.VISIBLE
        }
        binding.togle3.setOnClickListener {
            binding.togle3.visibility=View.GONE
            binding.plus3.visibility=View.VISIBLE
            binding.memoDetail.visibility=View.GONE
        }

        binding.memoDetail.setOnClickListener {
            binding.edit.visibility=View.VISIBLE
            binding.edit.setOnClickListener {
                binding.memoContext.visibility=View.GONE
                binding.edit.visibility=View.GONE
                binding.memoResult.visibility=View.VISIBLE
                // 선택되지 않은 경우
                    val Call: Call<AddMemo> =
                        ApiManager.mypageService.addReviewMemo(
                            "Bearer " + LocalDataSource.getAccessToken(), reviewId, binding.memoContext.text.toString()
                        )
                    // 비동기적으로 요청 수행
                    Call.enqueue(object : Callback<AddMemo> {
                        override fun onResponse(
                            call: Call<AddMemo>,
                            response: Response<AddMemo>
                        ) {
                            if (response.isSuccessful) {
                                val data = response.body()?.result
                                Log.d("함께 조회", data.toString())
                                Log.d("함께 서버", response.body()?.result.toString())

                            } else {
                                // 서버에서 오류 응답을 받은 경우 처리
                                Log.d("함께 서버", response.toString())
                            }

                        }

                        override fun onFailure(call: Call<AddMemo>, t: Throwable) {
                            // 통신 실패 처리
                            Log.d("함께 서버", t.message.toString())
                        }

                    })
            }

        }
    }
    private fun setPartnerSelection(icon: ImageView, tagText: TextView) {
        // 모든 아이콘 및 텍스트 선택 해제
        binding.icFamily.isSelected = false
        binding.icSmile.isSelected = false
        binding.icHeart.isSelected = false
        binding.tagText.isSelected = false
        binding.tagText2.isSelected = false
        binding.tagText3.isSelected = false
        binding.tagText4.isSelected = false

        // 선택된 아이콘 및 텍스트만 활성화
        icon.isSelected = true
        tagText.isSelected = true
    }


    override fun initAfterBinding() {
        super.initAfterBinding()

    }
}