package com.example.whashow

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.whashow.databinding.ActivityJoinBinding
import com.example.whashow.login.LocalDataSource

class JoinActivity: AppCompatActivity() {
    private lateinit var binding: ActivityJoinBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ActivityJoinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        LocalDataSource.init(this)

    }
}