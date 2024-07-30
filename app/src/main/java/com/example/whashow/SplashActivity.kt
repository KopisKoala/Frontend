package com.example.whashow

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        //스플래시 화면 표시 후 메인 액티비티로 이동
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@SplashActivity, JoinActivity::class.java)
            startActivity(intent)
            finish()
        }, 1000) // 2초 후에 메인 액티비티로 이동
    }
}