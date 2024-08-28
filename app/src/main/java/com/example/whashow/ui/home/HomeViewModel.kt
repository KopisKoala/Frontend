package com.example.whashow.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.whashow.apiManager.ApiManager
import com.example.whashow.data.Performance
import com.example.whashow.data.PerformanceAdvertiseResponse
import com.example.whashow.login.LocalDataSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val _performanceList = MutableLiveData<List<Performance>>()
    val performanceList: LiveData<List<Performance>> get() = _performanceList

    private val _popularList = MutableLiveData<List<Performance>>()
    val popularList: LiveData<List<Performance>> get() = _popularList

    private val _dramaList = MutableLiveData<List<Performance>>()
    val dramaList: LiveData<List<Performance>> get() = _dramaList


    private val _recommendList = MutableLiveData<List<Performance>>()
    val recommendList: LiveData<List<Performance>> get() = _recommendList

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchPerformanceData() {
        val call: Call<PerformanceAdvertiseResponse> = ApiManager.performanceService.getAdvertise(
            "Bearer " + LocalDataSource.getAccessToken()!!
        )

        call.enqueue(object : Callback<PerformanceAdvertiseResponse> {
            override fun onResponse(
                call: Call<PerformanceAdvertiseResponse>,
                response: Response<PerformanceAdvertiseResponse>
            ) {
                if (response.isSuccessful) {
                    _performanceList.value = response.body()?.result?.performanceList
                } else {
                    _error.value = "공연 정보를 불러오는데 실패했습니다."
                }
            }

            override fun onFailure(call: Call<PerformanceAdvertiseResponse>, t: Throwable) {
                _error.value = "공연 정보를 불러오는 중 오류가 발생했습니다: ${t.message}"
            }
        })
    }

    fun fetchPopularMusicalsData() {
        Log.d("실행", "실행됨")
        val call: Call<PerformanceAdvertiseResponse> = ApiManager.performanceService.getPopularMusicals(
            "Bearer " + LocalDataSource.getAccessToken()!!
        )

        call.enqueue(object : Callback<PerformanceAdvertiseResponse> {
            override fun onResponse(
                call: Call<PerformanceAdvertiseResponse>,
                response: Response<PerformanceAdvertiseResponse>
            ) {
                if (response.isSuccessful) {Log.d("실행", "실행됨 콜")

                    _popularList.value = response.body()?.result?.performanceList
                } else {
                    _error.value = "공연 정보를 불러오는데 실패했습니다."
                }
            }

            override fun onFailure(call: Call<PerformanceAdvertiseResponse>, t: Throwable) {
                _error.value = "공연 정보를 불러오는 중 오류가 발생했습니다: ${t.message}"
            }
        })
    }

    fun fetchRecommendData() {
        Log.d("실행", "실행됨")
        val call: Call<PerformanceAdvertiseResponse> = ApiManager.performanceService.getRecommend(
            "Bearer " + LocalDataSource.getAccessToken()!!
        )

        call.enqueue(object : Callback<PerformanceAdvertiseResponse> {
            override fun onResponse(
                call: Call<PerformanceAdvertiseResponse>,
                response: Response<PerformanceAdvertiseResponse>
            ) {
                if (response.isSuccessful) {Log.d("실행", "실행됨 추천")

                    _recommendList.value = response.body()?.result?.performanceList
                } else {
                    _error.value = "공연 정보를 불러오는데 실패했습니다."
                }
            }

            override fun onFailure(call: Call<PerformanceAdvertiseResponse>, t: Throwable) {
                _error.value = "공연 정보를 불러오는 중 오류가 발생했습니다: ${t.message}"
            }
        })
    }

    fun fetchPopularDramaData() {
        Log.d("실행", "실행됨")
        val call: Call<PerformanceAdvertiseResponse> = ApiManager.performanceService.getPopularDrama(
            "Bearer " + LocalDataSource.getAccessToken()!!
        )

        call.enqueue(object : Callback<PerformanceAdvertiseResponse> {
            override fun onResponse(
                call: Call<PerformanceAdvertiseResponse>,
                response: Response<PerformanceAdvertiseResponse>
            ) {
                if (response.isSuccessful) {Log.d("실행", "실행됨 추천")

                    _dramaList.value = response.body()?.result?.performanceList
                } else {
                    _error.value = "공연 정보를 불러오는데 실패했습니다."
                }
            }

            override fun onFailure(call: Call<PerformanceAdvertiseResponse>, t: Throwable) {
                _error.value = "공연 정보를 불러오는 중 오류가 발생했습니다: ${t.message}"
            }
        })
    }
}
