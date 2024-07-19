package com.example.whashow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.whashow.databinding.ActivityMainBinding
import com.example.whashow.ui.home.HomeFragment
import com.example.whashow.ui.mypage.MypageFragment
import com.example.whashow.ui.pairing.PairingFragment
import com.example.whashow.ui.recommand.RecommandResultFragment

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showInit()
        initBottomNav()

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
                    changeFragment(RecommandResultFragment())
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
}