package com.example.whashow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.whashow.apiManager.ApiManager
import com.example.whashow.data.KakaoLogin
import com.example.whashow.data.KakaoLoginRequest
import com.example.whashow.data.Reissue
import com.example.whashow.data.getNickname
import com.example.whashow.data.getNicknameRequest
import com.example.whashow.databinding.ActivityMainBinding
import com.example.whashow.login.LocalDataSource
import com.example.whashow.ui.home.HomeFragment
import com.example.whashow.ui.mypage.MypageFragment
import com.example.whashow.ui.pairing.PairingFragment
import com.example.whashow.ui.recommand.GenreFragment
import com.example.whashow.ui.recommand.RecommandResultFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showInit()
        initBottomNav()

        setSupportActionBar(binding.toolbar)

        // Disable displaying the title in the Toolbar
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager
                .popBackStack()
        }
        else {
            super.onBackPressed()
        }

    }

    fun ShowBack() {
        binding.btnBack.visibility = View.VISIBLE

        binding.toolbarLogo.visibility = View.GONE
        binding.btnMainSearch.visibility = View.GONE
        binding.mainTitle.visibility = View.GONE
        binding.backTitle.visibility = View.GONE
    }
    fun ShowBackandTitle() {
        binding.btnBack.visibility = View.VISIBLE
        binding.backTitle.visibility = View.VISIBLE

        binding.toolbarLogo.visibility = View.GONE
        binding.btnMainSearch.visibility = View.GONE
        binding.mainTitle.visibility = View.GONE
    }
    fun ShowTitle() {
        binding.mainTitle.visibility = View.VISIBLE

        binding.btnBack.visibility = View.GONE
        binding.backTitle.visibility = View.GONE
        binding.toolbarLogo.visibility = View.GONE
        binding.btnMainSearch.visibility = View.GONE

    }
    fun ShowLogoAndSearch(){
        binding.toolbarLogo.visibility = View.VISIBLE
        binding.btnMainSearch.visibility = View.VISIBLE

        binding.mainTitle.visibility = View.GONE
        binding.btnBack.visibility = View.GONE
        binding.backTitle.visibility = View.GONE
    }
    private fun showInit() {
        val transaction = supportFragmentManager.beginTransaction()
            .add(R.id.container_main, HomeFragment())
        transaction.commit()
    }

    private fun initBottomNav() {
        binding.navigationMain.itemIconTintList = null

        binding.navigationMain.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.navigation_home -> {
                    changeFragment(HomeFragment())
                }

                R.id.navigation_recommand -> {
                    addFragment(GenreFragment())
                }

                R.id.navigation_pairing -> {
                    changeFragment(PairingFragment())
                }
                R.id.navigation_mypage -> {
                    changeFragment(MypageFragment())
                }
            }
            return@setOnItemSelectedListener true
        }

        binding.navigationMain.setOnItemReselectedListener {  } // 바텀네비 재클릭시 화면 재생성 방지
    }
    fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.container_main, fragment).commit()
    }
    fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .commit {
                setCustomAnimations(
                    R.anim.slide_3,
                    R.anim.fade_out,
                    R.anim.slide_1,
                    R.anim.fade_out
                )
                replace(R.id.container_main, fragment)
                addToBackStack(null)
            }

    }
    fun reissue(){
        val Call: Call<Reissue> =
            ApiManager.loginService.reissue(LocalDataSource.getRefreshToken().toString())

        // 비동기적으로 요청 수행
        Call.enqueue(object : Callback<Reissue> {
            override fun onResponse(
                call: Call<Reissue>,
                response: Response<Reissue>
            ) {
                if (response.isSuccessful) {
                    val data=response.body()?.result
                    LocalDataSource.setAccessToken(data!!.accessToken)
                    LocalDataSource.setRefreshToken(data!!.refreshToken)

                } else {
                    // 서버에서 오류 응답을 받은 경우 처리
                    Log.d("로그인 토큰 서버", response.toString())
                }

            }

            override fun onFailure(call: Call<Reissue>, t: Throwable) {
                // 통신 실패 처리
                Log.d("로그인 토큰 서버", t.message.toString())
            }

        })
    }

}