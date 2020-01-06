package com.example.shin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_parent_home.*

class ParentHomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parent_home)

        supportActionBar?.hide() // 상단 바 감추기

        var name = intent.getStringExtra("name") // 이전 창에서 넘겨 받은 값으로 초기값 설정.
        txt_greet.setText(name + " 학부모님\n안녕하세요")
    }
}