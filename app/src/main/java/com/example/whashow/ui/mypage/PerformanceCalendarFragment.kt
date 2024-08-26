package com.example.whashow.ui.mypage

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.bumptech.glide.Glide
import com.example.whashow.R
import com.example.whashow.apiManager.ApiManager
import com.example.whashow.base.BaseFragment
import com.example.whashow.data.CalendarDayReview
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
    private val _partner = MutableLiveData<Int>()
    val partner: LiveData<Int>
        get() = _partner
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

                binding.calendarDetail.visibility=View.VISIBLE
                //함꼐 본 사람 수정
                binding.tagText.setOnClickListener {
                    /*binding.tagText.isSelected=!binding.tagText.isSelected
                    binding.icFamily.isSelected=!binding.icFamily.isSelected*/
                    _partner.value = 1
                }
                binding.tagText2.setOnClickListener {
                    /*binding.tagText2.isSelected=!binding.tagText2.isSelected
                    binding.icSmile.isSelected=!binding.icSmile.isSelected*/
                    _partner.value = 2
                }
                binding.tagText3.setOnClickListener {
                    /*binding.tagText3.isSelected=!binding.tagText3.isSelected
                    binding.icHeart.isSelected=!binding.icHeart.isSelected*/
                    _partner.value = 3
                }
                binding.tagText4.setOnClickListener {
                    /*binding.tagText4.isSelected=!binding.tagText4.isSelected
                    binding.icFamily.isSelected=!binding.icFamily.isSelected*/
                    _partner.value = 4
                }
                _partner.observe(this@PerformanceCalendarFragment, Observer { newPartner ->
                    // 선택되지 않은 경우
                    val Call: Call<Partner> =
                        ApiManager.mypageService.getPartner(
                            "Bearer " + LocalDataSource.getAccessToken(), id, newPartner
                        )
                    // 비동기적으로 요청 수행
                    Call.enqueue(object : Callback<Partner> {
                        override fun onResponse(
                            call: Call<Partner>,
                            response: Response<Partner>
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

                        override fun onFailure(call: Call<Partner>, t: Throwable) {
                            // 통신 실패 처리
                            Log.d("함께 서버", t.message.toString())
                        }

                    })

                    // 선택되지 않은 경우
                    val Call2: Call<CalendarDayReview> =
                        ApiManager.mypageService.getDayReview(
                            "Bearer " + LocalDataSource.getAccessToken(), id
                        )
                    // 비동기적으로 요청 수행
                    Call2.enqueue(object : Callback<CalendarDayReview> {
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
                                        .placeholder(R.drawable.img_poster_small) // 이미지 로딩 중에 표시될 placeholder 이미지
                                        .error(R.drawable.img_poster_small) // 이미지 로딩 실패 시 표시될 이미지
                                        .into(binding.imgPoster)

                                    binding.paringFeature.text = "#" + data.hashtag
                                    binding.performanceDate.text = data.performanceDate
                                    binding.posterTitle.text = data.performanceName
                                    if (data.performanceType == "MUSICAL") {
                                        binding.genre.text = "뮤지컬"
                                    } else {
                                        binding.genre.text = "연극"
                                    }
                                    binding.pairRating.rating = data.pairRatings.toFloat()
                                    binding.performanceRating.rating =
                                        data.performanceRatings.toFloat()

                                    //함꼐 본사람
                                    if (data.viewingPartner == "FAMILY") {
                                        binding.tagText.isSelected = true
                                        binding.icFamily.isSelected = true
                                    } else if (data.viewingPartner == "FRIEND") {
                                        binding.tagText2.isSelected = true
                                        binding.icSmile.isSelected = true
                                    } else if (data.viewingPartner == "COUPLE") {
                                        binding.tagText3.isSelected = true
                                        binding.icHeart.isSelected = true
                                    } else if (data.viewingPartner == "ALONE") {
                                        binding.tagText4.isSelected = true
                                        binding.icFamily.isSelected = true
                                    }
                                }
                                Log.d("일 목록 조회", data.toString())
                                Log.d("일 목록 조회 서버", response.body()?.result.toString())

                            } else {
                                // 서버에서 오류 응답을 받은 경우 처리
                                Log.d("일 목록 조회 서버", response.toString())
                            }

                        }

                        override fun onFailure(call: Call<CalendarDayReview>, t: Throwable) {
                            // 통신 실패 처리
                            Log.d("월 목록 조회 서버", t.message.toString())
                        }

                    })
                })
            }

        })
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

        binding.edit.setOnClickListener {
            binding.memoContext.isFocusableInTouchMode = !binding.memoContext.isFocusableInTouchMode
        }
    }


    override fun initAfterBinding() {
        super.initAfterBinding()

    }
}