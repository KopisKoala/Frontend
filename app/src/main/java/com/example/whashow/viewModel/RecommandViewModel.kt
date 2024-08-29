package com.example.whashow.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whashow.apiManager.ApiManager
import com.example.whashow.data.PerformanceResultDTOList
import com.example.whashow.data.PerformancesByStandard
import com.example.whashow.data.PerformancesByStandardList
import com.example.whashow.data.PopularPairRankResponse
import com.example.whashow.data.RecommandPairList
import com.example.whashow.data.RecommandPerformanceList
import com.example.whashow.data.RecommendPairResDto
import com.example.whashow.login.LocalDataSource
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecommandViewModel:ViewModel() {

    // GenreFragment에서 사용하는 데이터
    private val _genre = MutableLiveData<Int>()
    val genre: LiveData<Int> get() = _genre

    // DayandPlaceFragment에서 사용하는 데이터
    private val _startYear = MutableLiveData<Int>()
    private val _startMonth = MutableLiveData<Int>()
    private val _startDate = MutableLiveData<Int>()
    private val _endYear = MutableLiveData<Int>()
    private val _endMonth = MutableLiveData<Int>()
    private val _endDate = MutableLiveData<Int>()
    private val _location = MutableLiveData<String>()

    val startYear: LiveData<Int> get() = _startYear
    val startMonth: LiveData<Int> get() = _startMonth
    val startDate: LiveData<Int> get() = _startDate
    val endYear: LiveData<Int> get() = _endYear
    val endMonth: LiveData<Int> get() = _endMonth
    val endDate: LiveData<Int> get() = _endDate
    val location: LiveData<String> get() = _location

    // PriceFragment에서 사용하는 데이터
    private val _minPrice = MutableLiveData<Int>()
    private val _maxPrice = MutableLiveData<Int>()

    val minPrice: LiveData<Int> get() = _minPrice
    val maxPrice: LiveData<Int> get() = _maxPrice

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _recommandResultList = MutableLiveData<List<PerformancesByStandard>>()
    val recommandResultList: LiveData<List<PerformancesByStandard>> get() = _recommandResultList

    private val _performanceRecommandResultList = MutableLiveData<List<RecommendPairResDto>>()
    val performanceRecommandResultList: LiveData<List<RecommendPairResDto>> get() = _performanceRecommandResultList

    // 장르 설정 함수: LiveData로 관리
    fun setGenre(genre: Int) {
        _genre.value = genre
    }
    fun setRecommandList(recommandResultList: List<PerformancesByStandard>) {
        _recommandResultList.value = recommandResultList
    }

    fun setDayAndPlace(startYear: Int, startMonth: Int, startDate: Int, endYear: Int, endMonth: Int, endDate: Int, location: String) {
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

    fun callApi(genre: Int,startYear: Int, startMonth: Int, startDate: Int, endYear: Int, endMonth: Int, endDate: Int, location: String,minPrice: Int, maxPrice: Int) {
        Log.d("공연", "1")
        Log.d("공연", genre.toString())
        // 실제 API 호출 로직
        val call: Call<RecommandPerformanceList> = ApiManager.recommandService.getPerformanceList(
            "Bearer " + LocalDataSource.getAccessToken()!!,
            genre,
            startYear,
            startMonth,
            startDate,
            endYear,
            endMonth,
            endDate,
            location,
            minPrice,
            maxPrice
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

    fun fetchRecommandPair(perId:Int) {
        val call: Call<RecommandPairList> = ApiManager.recommandService.getPairList(
            "Bearer " + LocalDataSource.getAccessToken()!!,
            perId
        )

        call.enqueue(object : Callback<RecommandPairList> {
            override fun onResponse(
                call: Call<RecommandPairList>,
                response: Response<RecommandPairList>
            ) {
                if(response.isSuccessful) {
                    _performanceRecommandResultList.value = response.body()?.result?.recommendPairResDtoList ?: emptyList()
                    Log.d("페어 추천 조회 서버", _performanceRecommandResultList.toString())
                } else {
                    _error.value = "페어 추천을 불러오는데 실패했습니다."
                }
            }

            override fun onFailure(call: Call<RecommandPairList>, t: Throwable) {
                _error.value = "오류가 발생했습니다: ${t.message}"
            }
        })
    }


}
