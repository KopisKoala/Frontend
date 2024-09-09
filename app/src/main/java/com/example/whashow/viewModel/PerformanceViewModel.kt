package com.example.whashow.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.whashow.apiManager.ApiManager
import com.example.whashow.data.PerformanceResultDTOList
import com.example.whashow.data.PerformanceReview
import com.example.whashow.data.PerformancesByStandard
import com.example.whashow.data.RecommandPairList
import com.example.whashow.data.RecommandPerformanceList
import com.example.whashow.data.RecommendPairResDto
import com.example.whashow.login.LocalDataSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PerformanceViewModel : ViewModel() {

    private val _genre = MutableLiveData<Int>()
    val genre: LiveData<Int> get() = _genre

    private val _startYear = MutableLiveData<Int>()
    private val _startMonth = MutableLiveData<Int>()
    private val _startDate = MutableLiveData<Int>()
    private val _endYear = MutableLiveData<Int>()
    private val _endMonth = MutableLiveData<Int>()
    private val _endDate = MutableLiveData<Int>()
    private val _location = MutableLiveData<String>()

    private val _minPrice = MutableLiveData<Int>()
    private val _maxPrice = MutableLiveData<Int>()

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _recommandResultList = MutableLiveData<List<PerformancesByStandard>>()
    val recommandResultList: LiveData<List<PerformancesByStandard>> get() = _recommandResultList

    private val _performanceId = MutableLiveData<Int?>()
    val performanceId: LiveData<Int?> get() = _performanceId

    private val _reviewList = MutableLiveData<List<PerformanceResultDTOList>>()
    val reviewList: LiveData<List<PerformanceResultDTOList>> get() = _reviewList

    fun setGenre(genre: Int) {
        _genre.value = genre
    }

    fun setDayAndPlace(
        startYear: Int,
        startMonth: Int,
        startDate: Int,
        endYear: Int,
        endMonth: Int,
        endDate: Int,
        location: String
    ) {
        _startYear.value = startYear
        _startMonth.value = startMonth
        _startDate.value = startDate
        _endYear.value = endYear
        _endMonth.value = endMonth
        _endDate.value = endDate
        _location.value = location
    }

    fun setPrice(minPrice: Int, maxPrice: Int) {
        _minPrice.value = minPrice
        _maxPrice.value = maxPrice
    }

    fun setPerformanceId(performanceId: Int) {
        _performanceId.value = performanceId
    }

    fun fetchPerformanceData() {
        // 실제 API 호출 로직
        val call: Call<RecommandPerformanceList> = ApiManager.performanceService.getPerformanceList(
            "Bearer " + LocalDataSource.getAccessToken()!!,
            _genre.value!!,
            _startYear.value!!,
            _startMonth.value!!,
            _startDate.value!!,
            _endYear.value!!,
            _endMonth.value!!,
            _endDate.value!!,
            _location.value!!,
            _minPrice.value!!,
            _maxPrice.value!!
        )

        call.enqueue(object : Callback<RecommandPerformanceList> {
            override fun onResponse(
                call: Call<RecommandPerformanceList>,
                response: Response<RecommandPerformanceList>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()?.result
                    if (data != null) {
                        _recommandResultList.value = data.performancesByStandardList ?: emptyList()
                        _performanceId.value =
                            data.performancesByStandardList[0].performancesByStandard[0].id
                    }
                    Log.d("공연 추천 서버", response.body()?.result.toString())
                } else {
                    _error.value = "공연 추천 결과를 불러오는데 실패했습니다."
                    Log.d("공연 추천 서버", response.body()?.result.toString())
                }
            }

            override fun onFailure(call: Call<RecommandPerformanceList>, t: Throwable) {
                _error.value = "공연 추천 중 오류가 발생했습니다: ${t.message}"
                Log.d("공연 추천 서버", t.message.toString())
            }
        })
    }

    fun fetchPerformanceReview(perfId: Int, position: Int) {
        val sortList = listOf(
            "recent",
            "like",
            "asc",
            "desc"
        )
        val Call2: Call<PerformanceReview> =
            ApiManager.reviewService.getReview(
                "Bearer " + LocalDataSource.getAccessToken()!!, perfId, sortList[position]
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
                        _reviewList.value = data.reviewList
                    }

                    Log.d("페어 리뷰 목록 조회", data.toString())
                    Log.d("페어 리뷰 목록 조회 서버", response.body()?.result.toString())

                } else {
                    // 서버에서 오류 응답을 받은 경우 처리
                    Log.d("페어 리뷰 목록 조회 서버", response.toString())
                }

            }

            override fun onFailure(call: Call<PerformanceReview>, t: Throwable) {
                // 통신 실패 처리
                Log.d("페어 리뷰 목록 조회 서버", t.message.toString())
            }

        })

    }


}
