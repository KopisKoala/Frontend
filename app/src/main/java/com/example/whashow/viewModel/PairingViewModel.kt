package com.example.whashow.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.whashow.apiManager.ApiManager
import com.example.whashow.data.PairDetailResDtoByStandard
import com.example.whashow.data.PairReview
import com.example.whashow.data.PerformanceResultDTOList
import com.example.whashow.data.PerformancesByStandard
import com.example.whashow.data.RecommandPairList
import com.example.whashow.data.RecommandPerformanceList
import com.example.whashow.data.RecommendPairResDto
import com.example.whashow.data.Review
import com.example.whashow.login.LocalDataSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PairingViewModel : ViewModel() {

    private val _recommandResultList = MutableLiveData<List<RecommendPairResDto>>()
    val recommandResultList: LiveData<List<RecommendPairResDto>> get() = _recommandResultList

    private val _reviewList = MutableLiveData<List<Review>>()
    val reviewList: LiveData<List<Review>> get() = _reviewList

    private val _pairId = MutableLiveData<Int?>()
    val pairId: LiveData<Int?> get() = _pairId

    fun setPairId(pairId: Int) {
        _pairId.value = pairId
    }

    fun fetchPairingList(){
        val call: Call<RecommandPairList> = ApiManager.pairingService.getPairList(
            "Bearer " + LocalDataSource.getAccessToken()!!,1
        )

        call.enqueue(object : Callback<RecommandPairList> {
            override fun onResponse(
                call: Call<RecommandPairList>,
                response: Response<RecommandPairList>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()?.result
                    if (data != null) {
                        _recommandResultList.value = data.recommendPairResDtoList ?: emptyList()
                        _pairId.value =
                            data.recommendPairResDtoList[0].pairDetailResDtoByStandard.id
                    }
                    Log.d("공연 추천 서버", response.body()?.result.toString())
                } else {
                    Log.d("공연 추천 서버", response.body()?.result.toString())
                }
            }

            override fun onFailure(call: Call<RecommandPairList>, t: Throwable) {
                Log.d("공연 추천 서버", t.message.toString())
            }
        })
    }
    fun fetchPairingReview(pairId: Int, position:Int){
        val sortList = listOf(
            "recent",
            "like",
            "asc",
            "desc"
        )
        val call: Call<PairReview> = ApiManager.reviewService.getReviewPair(
            "Bearer " + LocalDataSource.getAccessToken()!!,pairId,sortList[position]
        )

        call.enqueue(object : Callback<PairReview> {
            override fun onResponse(
                call: Call<PairReview>,
                response: Response<PairReview>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()?.result
                    if (data != null) {
                        _reviewList.value = data.reviewList ?: emptyList()
                    }
                    Log.d("공연 추천 서버", response.body()?.result.toString())
                } else {
                    Log.d("공연 추천 서버", response.body()?.result.toString())
                }
            }

            override fun onFailure(call: Call<PairReview>, t: Throwable) {
                Log.d("공연 추천 서버", t.message.toString())
            }
        })
    }
}