package com.example.whashow

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.whashow.apiManager.ApiManager
import com.example.whashow.data.KakaoLogin
import com.example.whashow.data.KakaoLoginRequest
import com.example.whashow.data.getNickname
import com.example.whashow.data.getNicknameRequest
import com.example.whashow.databinding.ActivityJoinBinding
import com.example.whashow.login.LocalDataSource
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JoinActivity: AppCompatActivity() {
    private lateinit var binding: ActivityJoinBinding
    lateinit var kakaoCallback: (OAuthToken?, Throwable?) -> Unit
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val keyHash = Utility.getKeyHash(this)
        Log.d("키해시", keyHash)

        binding=ActivityJoinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        KakaoSdk.init(this, BuildConfig.NATIVE_APP_KEY)

        LocalDataSource.init(this)

        binding.kakaoLogin.setOnClickListener {
            kakaoLogin()
        }

    }
    fun kakaoLogin() {
        setKakaoCallback()
        UserApiClient.instance.loginWithKakaoAccount(this, callback = kakaoCallback)
    }

    fun setKakaoCallback() {
        kakaoCallback = { token, error ->
            if (error != null) {
                when {
                    error.toString() == AuthErrorCause.AccessDenied.toString() -> {
                        Log.d("[카카오로그인]", "접근이 거부 됨(동의 취소)")
                    }

                    error.toString() == AuthErrorCause.InvalidClient.toString() -> {
                        Log.d("[카카오로그인]", "유효하지 않은 앱")
                    }

                    error.toString() == AuthErrorCause.InvalidGrant.toString() -> {
                        Log.d("[카카오로그인]", "인증 수단이 유효하지 않아 인증할 수 없는 상태")
                    }

                    error.toString() == AuthErrorCause.InvalidRequest.toString() -> {
                        Log.d("[카카오로그인]", "요청 파라미터 오류")
                    }

                    error.toString() == AuthErrorCause.InvalidScope.toString() -> {
                        Log.d("[카카오로그인]", "유효하지 않은 scope ID")
                    }

                    error.toString() == AuthErrorCause.Misconfigured.toString() -> {
                        Log.d("[카카오로그인]", "설정이 올바르지 않음(android key hash)")
                    }

                    error.toString() == AuthErrorCause.ServerError.toString() -> {
                        Log.d("[카카오로그인]", "서버 내부 에러")
                    }

                    error.toString() == AuthErrorCause.Unauthorized.toString() -> {
                        Log.d("[카카오로그인]", "앱이 요청 권한이 없음")
                    }

                    else -> { // Unknown
                        Log.d("[카카오로그인]", "기타 에러")
                    }
                }
            } else if (token != null) {
                UserApiClient.instance.me { user, error ->
                    LocalDataSource.setUserId(user?.id.toString())
                    Log.d("[카카오로그인]", user?.kakaoAccount?.email.toString())

                    val atIndex = user?.kakaoAccount?.email.toString().indexOf("@")
                    val username= user?.kakaoAccount?.email.toString().substring(0, atIndex)

                    val Call: Call<KakaoLogin> =
                        ApiManager.loginService.login(KakaoLoginRequest(user?.kakaoAccount?.email.toString(),
                            "kakao","{kakao}$username"))

                    // 비동기적으로 요청 수행
                    Call.enqueue(object : Callback<KakaoLogin> {
                        override fun onResponse(
                            call: Call<KakaoLogin>,
                            response: Response<KakaoLogin>
                        ) {
                            if (response.isSuccessful) {
                                val data = response.body()?.result
                                LocalDataSource.setAccessToken(data!!.accessToken)
                                LocalDataSource.setRefreshToken(data!!.refreshToken)
                                if (data.signIn=="wasUser"){
                                    val intent = Intent(this@JoinActivity, MainActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                                else if (data.signIn=="newUser"){
                                    val intent = Intent(this@JoinActivity, WriteNicknameActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                                Log.d("로그인 토큰 서버", LocalDataSource.getAccessToken().toString())

                            } else {
                                // 서버에서 오류 응답을 받은 경우 처리
                                Log.d("로그인 토큰 서버", response.toString())
                            }

                        }

                        override fun onFailure(call: Call<KakaoLogin>, t: Throwable) {
                            // 통신 실패 처리
                            Log.d("로그인 토큰 서버", t.message.toString())
                        }

                    })


                }



            } else {
                Log.d("카카오로그인", "토큰==null error==null")
            }
        }
    }

}