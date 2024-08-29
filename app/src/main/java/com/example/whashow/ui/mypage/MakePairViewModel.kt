package com.example.whashow.ui.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.whashow.apiManager.ApiManager
import com.example.whashow.data.PairData
import com.example.whashow.data.PairDropdownResponse
import com.example.whashow.data.ReviewRequest
import com.example.whashow.data.ReviewResponse
import com.example.whashow.login.LocalDataSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MakePairViewModel : ViewModel() {

    private val _pairList = MutableLiveData<List<PairData>>()
    val pairList: LiveData<List<PairData>> get() = _pairList

    private val _pairId = MutableLiveData<List<Int>>()
    val pairId: LiveData<List<Int>> get() = _pairId

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _reviewSaveStatus = MutableLiveData<String>()
    val reviewSaveStatus: LiveData<String> get() = _reviewSaveStatus

    fun fetchPairData(performance : Int){
                val call: Call<PairDropdownResponse> = ApiManager.mypageService.getPairDropDown(
                    "Bearer " + LocalDataSource.getAccessToken()!!
                    ,performanceId = performance
                )

                call.enqueue(object : Callback<PairDropdownResponse> {
                    override fun onResponse(
                        call: Call<PairDropdownResponse>,
                        response: Response<PairDropdownResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { body ->
                        _pairList.value = body.result.pairList
                        _pairId.value = body.result.pairList.map { it.pairId }
                    }
                } else {
                    _error.value = "페어 정보를 불러오는데 실패했습니다."
                }
            }

            override fun onFailure(call: Call<PairDropdownResponse>, t: Throwable) {
                _error.value = "페어 정보를 불러오는 중 오류가 발생했습니다: ${t.message}"
            }
        })
    }

    fun saveReview(
        performanceId: Int,
        performanceRating: Int,
        pairId: Int,
        pairRating: Int,
        hashTag: String,
        content: String,
        performanceDate: String
    ) {
        val reviewRequest = ReviewRequest(
            performanceId = performanceId,
            performanceRating = performanceRating,
            pairId = pairId,
            pairRating = pairRating,
            hashTag = hashTag,
            content = content,
            performanceDate = performanceDate
        )

        val call: Call<ReviewResponse> = ApiManager.mypageService.createReview(
            "Bearer " + LocalDataSource.getAccessToken()!!,
            reviewRequest
        )

        call.enqueue(object : Callback<ReviewResponse> {
            override fun onResponse(
                call: Call<ReviewResponse>,
                response: Response<ReviewResponse>
            ) {
                if (response.isSuccessful) {
                    _reviewSaveStatus.value = response.body()?.message
                } else {
                    _error.value = "리뷰를 저장하는 데 실패했습니다."
                }
            }

            override fun onFailure(call: Call<ReviewResponse>, t: Throwable) {
                _error.value = "리뷰를 저장하는 중 오류가 발생했습니다: ${t.message}"
            }
        })
    }


}