package com.example.whashow.apiManager

import com.example.whashow.BuildConfig
import com.example.whashow.api.LoginService
import com.example.whashow.api.MypageService
import com.example.whashow.api.PairingService
import com.example.whashow.api.PerformanceService
import com.example.whashow.api.RecommandService
import com.example.whashow.api.SearchService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiManager {

    private var BASE_URL = BuildConfig.AUTH_BASE_URL

    val loggingInterceptor= HttpLoggingInterceptor().apply {
        level= HttpLoggingInterceptor.Level.BODY
    }

    val gson : Gson = GsonBuilder().setLenient().create()

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(100, TimeUnit.SECONDS) // 연결 타임아웃 설정 (기본값은 10초)
        .readTimeout(100, TimeUnit.SECONDS)    // 읽기 타임아웃 설정 (기본값은 10초)
        .writeTimeout(100, TimeUnit.SECONDS)   // 쓰기 타임아웃 설정 (기본값은 10초)
        .build()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            //.addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .client(okHttpClient)
            .build()
    }

    val loginService: LoginService = retrofit.create(LoginService::class.java)
    val mypageService: MypageService = retrofit.create(MypageService::class.java)
    val pairingService: PairingService = retrofit.create(PairingService::class.java)
    val performanceService: PerformanceService = retrofit.create(PerformanceService::class.java)
    val searchService : SearchService = retrofit.create(SearchService::class.java)
    val recommandService : RecommandService = retrofit.create(RecommandService::class.java)

}