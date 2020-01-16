package com.example.shin

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.example.shin.fragments.AfterSchool
import com.example.shin.fragments.Home
import com.google.android.material.bottomnavigation.BottomNavigationView


class ParentHomeActivity : AppCompatActivity(){
    val mContext: Context = this

    lateinit var afterschool : AfterSchool
    lateinit var home : Home

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parent_home)

        supportActionBar?.hide() // 상단 바 감추기

        /*
        var name = intent.getStringExtra("name") // 이전 창에서 넘겨 받은 값으로 초기값 설정.
        txt_greet.setText(name + " 학부모님\n안녕하세요")
        */

        val bottomnavigation : BottomNavigationView = findViewById(R.id.btm_nav)

        bottomnavigation.setSelectedItemId(R.id.menuitem_bottom_afterschool)

        afterschool = AfterSchool()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_blank, afterschool)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()

        bottomnavigation.setOnNavigationItemReselectedListener { item ->
            when (item.itemId) {

                R.id.menuitem_bottombar_home -> {
                    home = Home()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_blank, home)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }

                R.id.menuitem_bottom_afterschool -> {
                    afterschool = AfterSchool()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_blank, afterschool)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }
            }
        }


        /*bottomnavigation.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menuitem_bottombar_home -> {
                    home = Home()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_blank, home)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }

                R.id.menuitem_bottom_afterschool -> {
                    afterschool = AfterSchool()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_blank, afterschool)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }
            }
            false
        })*/





    }
}