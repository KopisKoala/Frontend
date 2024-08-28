package com.example.whashow.ui.home

import Actor
import Performance
import SearchResponse
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.whashow.apiManager.ApiManager.searchService
import com.example.whashow.login.LocalDataSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : ViewModel() {

    private val _actorList = MutableLiveData<List<Actor>>()
    val actorList: LiveData<List<Actor>> get() = _actorList

    private val _performanceList = MutableLiveData<List<Performance>>()
    val performanceList: LiveData<List<Performance>> get() = _performanceList

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchSearchHome(query: String) {
        val call: Call<SearchResponse> = searchService.getSearchHome(
            "Bearer " + LocalDataSource.getAccessToken()!!,
            query = query,
            page = 0
        )

        call.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                if (response.isSuccessful) {
                    _performanceList.value = response.body()?.result?.performances?.performanceList ?: emptyList()
                    _actorList.value = response.body()?.result?.actors?.actorList ?: emptyList()
                } else {
                    _error.value = "검색 결과를 불러오는데 실패했습니다."
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                _error.value = "검색 중 오류가 발생했습니다: ${t.message}"
            }
        })
    }
}
