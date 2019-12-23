package com.example.shin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_second.*


class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        supportActionBar?.hide()


        bt_parent.setOnClickListener(
            {
                var myIntent = Intent(this, MainActivity::class.java)
                startActivity(myIntent)
            }
        )
    }
}
