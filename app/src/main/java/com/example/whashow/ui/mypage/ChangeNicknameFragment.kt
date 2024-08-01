package com.example.whashow.ui.mypage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.whashow.MainActivity
import com.example.whashow.R
import com.example.whashow.apiManager.ApiManager
import com.example.whashow.base.BaseFragment
import com.example.whashow.data.getNickname
import com.example.whashow.data.getNicknameRequest
import com.example.whashow.databinding.FragmentChangeNicknameBinding
import com.example.whashow.login.LocalDataSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangeNicknameFragment : BaseFragment<FragmentChangeNicknameBinding>(R.layout.fragment_change_nickname) {
    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).ShowBackandTitle()
        (activity as MainActivity).binding.backTitle.text="닉네임 변경"
        (activity as MainActivity).binding.navigationMain.visibility=View.GONE
    }

    override fun initDataBinding() {
        super.initDataBinding()

        binding.changeNickname.setOnClickListener {

            val nickName = binding.edit.text.toString()
            if (nickName.isNotEmpty()) {
                val Call2: Call<getNickname> =
                    ApiManager.loginService.getNickname(
                        "Bearer " + LocalDataSource.getAccessToken()!!, getNicknameRequest(nickName)
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
                            Toast.makeText(requireContext(), "닉네임이 변경되었습니다!.", Toast.LENGTH_SHORT).show()
                            (activity as MainActivity).onBackPressed()

                        } else {
                            // 서버에서 오류 응답을 받은 경우 처리
                            Log.d("닉네임 서버", response.toString())
                            Toast.makeText(requireContext(), "중복된 이름입니다.", Toast.LENGTH_SHORT).show()
                        }

                    }

                    override fun onFailure(call: Call<getNickname>, t: Throwable) {
                        // 통신 실패 처리
                        Log.d("닉네임 서버", t.message.toString())
                    }

                })

            } else {
                binding.changeNickname.setOnClickListener {
                    Toast.makeText(requireContext(), "닉네임 입력을 완료해주세요.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun initAfterBinding() {
        super.initAfterBinding()

    }
}