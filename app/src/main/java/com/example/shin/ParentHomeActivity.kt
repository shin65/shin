package com.example.shin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class ParentHomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parent_home)

        supportActionBar?.hide() // 상단 바 감추기


    }
}