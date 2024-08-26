package com.example.whashow.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.whashow.apiManager.ApiManager
import com.example.whashow.data.DetailActor
import com.example.whashow.data.PerformanceDetailResponse
import com.example.whashow.data.PerformanceDetailResult
import com.example.whashow.login.LocalDataSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PerformanceDetailViewModel : ViewModel() {

    private val _performanceDetail = MutableLiveData<PerformanceDetailResult>()
    val performanceDetail: LiveData<PerformanceDetailResult> get() = _performanceDetail

    private val _actorList = MutableLiveData<List<DetailActor>>()
    val actorList: LiveData<List<DetailActor>> get() = _actorList

    private val _hashtags = MutableLiveData<List<String>>()
    val hashtags: LiveData<List<String>> get() = _hashtags

    private val _rating = MutableLiveData<Float>()
    val rating: LiveData<Float> get() = _rating


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


                        _actorList.value = result?.performanceDetailActorListResDto?.actorList
                        // 해시태그를 리스트로 업데이트
                        _hashtags.value = listOfNotNull(result?.hashtag1, result?.hashtag2, result?.hashtag3)
                        // 평점 업데이트
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

    fun fetchPerformanceReview(){

    }
}
