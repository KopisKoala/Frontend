package com.example.whashow.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.whashow.apiManager.ApiManager
import com.example.whashow.data.PairReview
import com.example.whashow.data.PerformanceResultDTOList
import com.example.whashow.data.Review
import com.example.whashow.login.LocalDataSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecommandResultViewModel:ViewModel() {
    private val _pairId = MutableLiveData<Int?>()
    val pairId: LiveData<Int?> get() = _pairId

    private val _reviewList = MutableLiveData<List<PerformanceResultDTOList>>()
    val reviewList: LiveData<List<PerformanceResultDTOList>> get() = _reviewList

    /*fun fetchPerformanceReview(pairId: Int,position:Int){
        //정렬
        val sortList = listOf(
            "recent",
            "like",
            "asc",
            "desc"
        )
        val Call2: Call<PairReview> =
            ApiManager.pairingService.getInfo(
                "Bearer " + LocalDataSource.getAccessToken()!!, pairId,sortList[position]
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
                        _reviewList.value=data.reviewList ?: emptyList()
                    }

                    Log.d("페어 리뷰 목록 조회", data.toString())
                    Log.d("페어 리뷰 목록 조회 서버", response.body()?.result.toString())

                } else {
                    // 서버에서 오류 응답을 받은 경우 처리
                    Log.d("페어 리뷰 목록 조회 서버", response.toString())
                }

            }

            override fun onFailure(call: Call<PairReview>, t: Throwable) {
                // 통신 실패 처리
                Log.d("페어 리뷰 목록 조회 서버", t.message.toString())
            }

        })

    }*/
    fun setPairId(pairId: Int) {
        _pairId.value = pairId
    }
    fun setReviewList(reviewList: List<PerformanceResultDTOList>) {
        _reviewList.value = reviewList
    }


}