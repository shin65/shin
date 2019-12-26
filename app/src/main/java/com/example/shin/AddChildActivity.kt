package com.example.shin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.example.shin.utils.ConnectServer
import kotlinx.android.synthetic.main.activity_main.*

class AddChildActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_child)

        supportActionBar?.hide()

        bt_back.setOnClickListener{
            var myIntent = Intent(this, MainActivity::class.java)
            startActivity(myIntent)}

    }
}