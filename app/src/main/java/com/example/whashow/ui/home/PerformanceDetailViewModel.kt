package com.example.whashow.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.whashow.apiManager.ApiManager
import com.example.whashow.data.DetailActor
import com.example.whashow.data.LikeResponse
import com.example.whashow.data.PerformanceDetailResponse
import com.example.whashow.data.PerformanceDetailResult
import com.example.whashow.data.PerformanceResultDTOList
import com.example.whashow.data.PerformanceReview
import com.example.whashow.login.LocalDataSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PerformanceDetailViewModel : ViewModel() {

    private val _likeResponse = MutableLiveData<Boolean>()
    val likeResponse: LiveData<Boolean> get() = _likeResponse

    private val _ticketingLink = MutableLiveData<String>()
    val ticketingLink: LiveData<String> get() = _ticketingLink

    private val _performanceDetail = MutableLiveData<PerformanceDetailResult>()
    val performanceDetail: LiveData<PerformanceDetailResult> get() = _performanceDetail

    private val _actorList = MutableLiveData<List<DetailActor>>()
    val actorList: LiveData<List<DetailActor>> get() = _actorList

    private val _hashtags = MutableLiveData<List<String>>()
    val hashtags: LiveData<List<String>> get() = _hashtags

    private val _rating = MutableLiveData<Float>()
    val rating: LiveData<Float> get() = _rating

    private val _reviewList = MutableLiveData<List<PerformanceResultDTOList>>()
    val reviewList: LiveData<List<PerformanceResultDTOList>> get() = _reviewList


    fun fetchPerformanceData(performanceId: Int) {
        val call: Call<PerformanceDetailResponse> = ApiManager.performanceService.getDetailInfo(
            "Bearer " + LocalDataSource.getAccessToken()!!, performanceId
        )

        call.enqueue(object : Callback<PerformanceDetailResponse> {
            override fun onResponse(
                call: Call<PerformanceDetailResponse>,
                response: Response<PerformanceDetailResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.result?.let { result ->
                        _performanceDetail.value = result
                        _ticketingLink.value = result?.ticketingLink
                        _actorList.value = result?.performanceDetailActorListResDto?.actorList
                        _hashtags.value = listOfNotNull(result?.hashtag1, result?.hashtag2, result?.hashtag3)
                        _rating.value = result?.ratingAverage ?: 0f
                    }
                } else {
                    // 실패 처리
                }
            }

            override fun onFailure(call: Call<PerformanceDetailResponse>, t: Throwable) {
                // 실패 처리
            }
        })
    }


    fun postLikeActor(actorId: Int) {
        val call: Call<LikeResponse> = ApiManager.performanceService.postLike(
            "Bearer " + LocalDataSource.getAccessToken()!!, actorId
        )

        call.enqueue(object : Callback<LikeResponse> {
            override fun onResponse(call: Call<LikeResponse>, response: Response<LikeResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { likeResponse ->
                        if (likeResponse.isSuccess) {
                            _likeResponse.value = true
                        } else {
                            _likeResponse.value = false
                        }
                    }
                } else {
                    _likeResponse.value = false
                    // 실패 처리: 서버에서 응답은 왔지만 성공하지 못한 경우
                }
            }

            override fun onFailure(call: Call<LikeResponse>, t: Throwable) {
                _likeResponse.value = false
                // 실패 처리: 네트워크 문제 등으로 요청이 실패한 경우
            }
        })
    }

    fun postDisLikeActor(actorId: Int) {
        val call: Call<LikeResponse> = ApiManager.performanceService.postDisLike(
            "Bearer " + LocalDataSource.getAccessToken()!!, actorId
        )
        call.enqueue(object : Callback<LikeResponse> {
            override fun onResponse(call: Call<LikeResponse>, response: Response<LikeResponse>) {
                _likeResponse.value = response.isSuccessful && response.body()?.isSuccess == true
            }

            override fun onFailure(call: Call<LikeResponse>, t: Throwable) {
                _likeResponse.value = false
            }
        })
    }

    fun fetchPerformanceReview(performanceId: Int,position:Int){
        //정렬
        val sortList = listOf(
            "recent",
            "like",
            "asc",
            "desc"
        )
        val Call2: Call<PerformanceReview> =
            ApiManager.reviewService.getReview(
                "Bearer " + LocalDataSource.getAccessToken()!!, performanceId,sortList[position]
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
                        _reviewList.value=data.reviewList ?: emptyList()
                    }

                    Log.d("리뷰 목록 조회", data.toString())
                    Log.d("리뷰 목록 조회 서버", response.body()?.result.toString())

                } else {
                    // 서버에서 오류 응답을 받은 경우 처리
                    Log.d("리뷰 목록 조회 서버", response.toString())
                }

            }

            override fun onFailure(call: Call<PerformanceReview>, t: Throwable) {
                // 통신 실패 처리
                Log.d("리뷰 목록 조회 서버", t.message.toString())
            }

        })

    }

}
