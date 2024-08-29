package com.example.whashow.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whashow.apiManager.ApiManager
import com.example.whashow.data.RecommandPerformanceList
import com.example.whashow.login.LocalDataSource
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecommandViewModel:ViewModel() {
    private val _apiResult = MutableLiveData<ApiResult>()
    val apiResult: LiveData<ApiResult> get() = _apiResult

    private val _genre = MutableLiveData<Int?>()
    val genre: LiveData<Int?> get() = _genre

    private var startYear: Int? = null
    private var startMonth: Int? = null
    private var startDate: Int? = null
    private var endYear: Int? = null
    private var endMonth: Int? = null
    private var endDate: Int? = null
    private var location: String? = null
    private var minPrice: Int? = null
    private var maxPrice: Int? = null

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    // 장르 설정 함수: LiveData로 관리
    fun setGenre(genre: Int) {
        _genre.value = genre
    }

    fun setDayAndPlace(startYear: Int, startMonth: Int, startDate: Int, endYear: Int, endMonth: Int, endDate: Int, location: String) {
        this.startYear = startYear
        this.startMonth = startMonth
        this.startDate = startDate
        this.endYear = endYear
        this.endMonth = endMonth
        this.endDate = endDate
        this.location = location
    }

    fun setPrice(minPrice: Int, maxPrice: Int) {
        this.minPrice = minPrice
        this.maxPrice = maxPrice
    }

    fun callApi() {
        viewModelScope.launch {
            if (genre != null && startYear != null && startMonth != null && startDate != null &&
                endYear != null && endMonth != null && endDate != null && location != null &&
                minPrice != null && maxPrice != null) {

                val result = apiCall( _genre.value!!, startYear!!, startMonth!!, startDate!!, endYear!!, endMonth!!, endDate!!, location!!, minPrice!!, maxPrice!!)
                _apiResult.value = result
            }
        }
    }

    private suspend fun apiCall(
        genre: Int,
        startYear: Int,
        startMonth: Int,
        startDate: Int,
        endYear: Int,
        endMonth: Int,
        endDate: Int,
        location: String,
        minPrice: Int,
        maxPrice: Int
    ): ApiResult {

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
        return ApiResult.Success
    }
}
sealed class ApiResult {
    object Success : ApiResult()
    data class Failure(val error: String) : ApiResult()
}