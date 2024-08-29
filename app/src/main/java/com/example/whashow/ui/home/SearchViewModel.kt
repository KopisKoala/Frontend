package com.example.whashow.ui.home

import Actor
import Performance
import SearchHomeResponse
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.whashow.apiManager.ApiManager.searchService
import com.example.whashow.data.PairDetailListResDto
import com.example.whashow.data.PopularPairDetailResDto
import com.example.whashow.data.PopularPairRankResponse
import com.example.whashow.data.SearchPairResponse
import com.example.whashow.login.LocalDataSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : ViewModel() {

    private val _actorList = MutableLiveData<List<Actor>>()
    val actorList: LiveData<List<Actor>> get() = _actorList

    private val _reviewCount = MutableLiveData<Int>()
    val reviewCount: LiveData<Int> get() = _reviewCount

    private val _performanceList = MutableLiveData<List<Performance>>()
    val performanceList: LiveData<List<Performance>> get() = _performanceList

    private val _popularPairList = MutableLiveData<List<PopularPairDetailResDto>>()
    val popularPairList: LiveData<List<PopularPairDetailResDto>> get() = _popularPairList

    private val _searchPairList = MutableLiveData<List<PairDetailListResDto>>()
    val searchPairList: LiveData<List<PairDetailListResDto>> get() = _searchPairList

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchSearchHome(query: String) {
        val call: Call<SearchHomeResponse> = searchService.getSearchHome(
            "Bearer " + LocalDataSource.getAccessToken()!!,
            query = query,
            page = 0
        )

        call.enqueue(object : Callback<SearchHomeResponse> {
            override fun onResponse(
                call: Call<SearchHomeResponse>,
                response: Response<SearchHomeResponse>
            ) {
                if (response.isSuccessful) {
                    _performanceList.value = response.body()?.result?.performances?.performanceList ?: emptyList()
                    _actorList.value = response.body()?.result?.actors?.actorList ?: emptyList()
                } else {
                    _error.value = "검색 결과를 불러오는데 실패했습니다."
                }
            }

            override fun onFailure(call: Call<SearchHomeResponse>, t: Throwable) {
                _error.value = "검색 중 오류가 발생했습니다: ${t.message}"
            }
        })
    }

    // 페어링 검색
    fun fetchSearchPair(query: String) {
        val call: Call<SearchPairResponse> = searchService.getSearchPair(
            "Bearer " + LocalDataSource.getAccessToken()!!,
            query = query,
            page = 0
        )

        call.enqueue(object : Callback<SearchPairResponse> {
            override fun onResponse(
                call: Call<SearchPairResponse>,
                response: Response<SearchPairResponse>
            ) {
                if (response.isSuccessful) {
                    val pairs = response.body()?.result?.pairDetailListResDtos ?: emptyList()
                    _searchPairList.value = pairs

                    // 리뷰 카운트 추출 및 설정
                    val totalReviewCount = pairs.sumOf { pairList ->
                        pairList.pairDetailResDtoList.sumOf { it.reviewCount }
                    }
                    _reviewCount.value = totalReviewCount

                } else {
                    _error.value = "검색 결과를 불러오는데 실패했습니다."
                }
            }

            override fun onFailure(call: Call<SearchPairResponse>, t: Throwable) {
                _error.value = "검색 중 오류가 발생했습니다: ${t.message}"
            }
        })
    }


    // 인기 페어 랭킹 반환 함수
    fun fetchPopularPair() {
        val call: Call<PopularPairRankResponse> = searchService.getPopularPair(
            "Bearer " + LocalDataSource.getAccessToken()!!,
            size = 10
        )

        call.enqueue(object : Callback<PopularPairRankResponse> {
            override fun onResponse(
                call: Call<PopularPairRankResponse>,
                response: Response<PopularPairRankResponse>
            ) {
                if(response.isSuccessful) {
                    _popularPairList.value = response.body()?.result?.pairDetailResDtoList ?: emptyList()
                    Log.d("인기 페어 조회 서버", _performanceList.toString())
                } else {
                    _error.value = "인기 페어 랭킹을 불러오는데 실패했습니다."
                }
            }

            override fun onFailure(call: Call<PopularPairRankResponse>, t: Throwable) {
                _error.value = "오류가 발생했습니다: ${t.message}"
            }
        })
    }

//    // 공연 검색에 따른 페어 반환
//    fun fetchPerformanceSearchPair(query: String) {
//        val call: Call<SearchPairResponse> = searchService.getSearchPair(
//            "Bearer " + LocalDataSource.getAccessToken()!!,
//            query,
//            0
//        )
//
//        call.enqueue(object : Callback<SearchPairResponse> {
//            override fun onResponse(
//                call: Call<SearchPairResponse>,
//                response: Response<SearchPairResponse>
//            ) {
//                if(response.isSuccessful) {
//                    response.body()?.result?.let { result ->
//                        val mergedList = mutableListOf<PairDetailResDto>()
//                        result.pairDetailListResDtos.forEach { pairDetailListResDto ->
//                            mergedList.addAll(pairDetailListResDto.pairDetailResDtoList)
//                        }
//                        Log.d("검색 페어 조회 서버", mergedList.toString())
//                        _searchPairList.value = mergedList
//                    } ?: run {
//                        _searchPairList.value = emptyList()
//                    }
//                    Log.d("검색 페어 조회 서버", _searchPairList.toString())
//                } else {
//                    _error.value = "검색 페어를 불러오는데 실패했습니다."
//                }
//            }
//
//            override fun onFailure(call: Call<SearchPairResponse>, t: Throwable) {
//                _error.value = "오류가 발생했습니다: ${t.message}"
//            }
//        })

}
