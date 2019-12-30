package com.example.shin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler


class FirstActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activiry_first)

        supportActionBar?.hide() // 상단 바 감추기

        Handler().postDelayed({
            startActivity(Intent(this,SecondActivity::class.java))
        },1500) // 자동 화면 넘김.

    }
}
