package com.example.whashow

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.example.whashow.apiManager.ApiManager
import com.example.whashow.data.getNickname
import com.example.whashow.data.getNicknameRequest
import com.example.whashow.databinding.ActivityJoinBinding
import com.example.whashow.databinding.ActivityWriteNicknameBinding
import com.example.whashow.login.LocalDataSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WriteNicknameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWriteNicknameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_nickname)

        binding= ActivityWriteNicknameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStart.setOnClickListener {
            // EditText에서 닉네임을 가져옴
            val nickName = binding.editText.text.toString()

            if (nickName.isNotEmpty()) {
                val Call2: Call<getNickname> =
                    ApiManager.loginService.getNickname(
                        "Bearer "+ LocalDataSource.getAccessToken()!!, getNicknameRequest(nickName)
                    )
                // 비동기적으로 요청 수행
                Call2.enqueue(object : Callback<getNickname> {
                    override fun onResponse(
                        call: Call<getNickname>,
                        response: Response<getNickname>
                    ) {
                        if (response.isSuccessful) {
                            val data = response.body()?.result.toString()
                            Log.d("닉네임", data)
                            Log.d("닉네임 서버", response.body()?.result.toString())
                            val intent = Intent(this@WriteNicknameActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()

                        } else {
                            // 서버에서 오류 응답을 받은 경우 처리
                            Log.d("닉네임 서버", response.toString())
                            Toast.makeText(this@WriteNicknameActivity, "중복된 이름입니다.", Toast.LENGTH_SHORT).show()
                        }

                    }

                    override fun onFailure(call: Call<getNickname>, t: Throwable) {
                        // 통신 실패 처리
                        Log.d("닉네임 서버", t.message.toString())
                    }

                })

            }
            else {
                Toast.makeText(this, "닉네임 입력을 완료해주세요.", Toast.LENGTH_SHORT).show()
            }

        }

    }
}