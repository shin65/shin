package com.example.shin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_class_apply.*

class ApplyClassActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_class_apply)

        supportActionBar?.hide()

        bt_lecture_back.setOnClickListener(){
            finish()
        }
    }
}