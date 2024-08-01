package com.example.whashow.ui.mypage

import android.Manifest
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.whashow.MainActivity
import com.example.whashow.R
import com.example.whashow.apiManager.ApiManager
import com.example.whashow.base.BaseFragment
import com.example.whashow.data.AddProfile
import com.example.whashow.data.getNickname
import com.example.whashow.data.getNicknameRequest
import com.example.whashow.databinding.FragmentMemberInfoBinding
import com.example.whashow.databinding.FragmentPairingBinding
import com.example.whashow.login.LocalDataSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MemberInfoFragment : BaseFragment<FragmentMemberInfoBinding>(R.layout.fragment_member_info) {

    private var imageUri: Uri? = null
    // 갤러리 open
    private val requestPermissionLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                openGallery()
            }
        }
    // 가져온 사진 보여주기
    private val pickImageLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                data?.data?.let {uri ->
                    imageUri = uri
                    context?.let { context ->
                        Glide.with(context)
                            .load(imageUri)
                            .placeholder(R.drawable.img_profile) // 이미지 로딩 중에 표시될 placeholder 이미지
                            .error(R.drawable.img_profile) // 이미지 로딩 실패 시 표시될 이미지
                            .into(binding.profile)

                        val Call: Call<AddProfile> =
                            ApiManager.mypageService.changeProfile(
                                "Bearer "+ LocalDataSource.getAccessToken()!!, imageUri.toString())
                        // 비동기적으로 요청 수행
                        Call.enqueue(object : Callback<AddProfile> {
                            override fun onResponse(
                                call: Call<AddProfile>,
                                response: Response<AddProfile>
                            ) {
                                if (response.isSuccessful) {
                                    val data = response.body()?.result.toString()
                                    Log.d("프로필 수정 서버", response.body()?.result.toString())

                                } else {
                                    // 서버에서 오류 응답을 받은 경우 처리
                                    Log.d("프로필 수정 서버", response.toString())
                                }

                            }
                            override fun onFailure(call: Call<AddProfile>, t: Throwable) {
                                // 통신 실패 처리
                                Log.d("프로필 수정 서버", t.message.toString())
                            }

                        })
                    }
                }
            }
        }
    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).binding.backTitle.text="회원정보"
        (activity as MainActivity).ShowBackandTitle()
        (activity as MainActivity).binding.navigationMain.visibility=View.VISIBLE
    }

    override fun initDataBinding() {
        super.initDataBinding()

        binding.changeProfileImg.setOnClickListener {
            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU){
                checkPermission(Manifest.permission.READ_MEDIA_IMAGES)
            }
            else {
                checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }

        binding.changeNickname.setOnClickListener {
            (activity as MainActivity).addFragment(ChangeNicknameFragment())
        }
    }
    private fun checkPermission(permission: String) {
        if (ContextCompat.checkSelfPermission(requireContext(), permission) == PackageManager.PERMISSION_GRANTED) {
            openGallery()
            Log.d("check", "Permission granted, opening gallery")
        } else {
            requestPermissionLauncher.launch(permission)
            Log.d("check", "Permission not granted, requesting permission")
            Log.d("check", ContextCompat.checkSelfPermission(requireContext(), permission).toString())
        }
    }

    private fun openGallery() {
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        pickImageLauncher.launch(gallery)
    }

    override fun initAfterBinding() {
        super.initAfterBinding()

    }
}