package com.example.shin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shin.utils.GlobalData
import kotlinx.android.synthetic.main.activity_second.*


class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        supportActionBar?.hide()


        bt_parent.setOnClickListener()
        {
            var myIntent :Intent

            println("동의 : " + GlobalData.loginUser?.terms_time)
            if(GlobalData.loginUser?.terms_time == "null") {
                myIntent = Intent(this, TermsActivity::class.java)
                myIntent.putExtra("type", "PARENTS") // 다음창으로 값 넘김.
                startActivity(myIntent)
            }
            else if(GlobalData.loginUser?.child == null){
                println("자식확인.")
                myIntent = Intent(this, AddChildActivity::class.java)
                myIntent.putExtra("type", "PARENTS") // 다음창으로 값 넘김.
                startActivity(myIntent)
            }
            else if(GlobalData.loginUser?.profile_image_url == "null"){
                println("사진확인")
                myIntent = Intent(this, ParentProfileActivity::class.java)
                myIntent.putExtra("type", "PARENTS") // 다음창으로 값 넘김.
                startActivity(myIntent)
                //프로필등록으로
            }
            else{
                println("사진확인 : " + GlobalData.loginUser?.profile_image_url)
                println("학부모홈으로")
                myIntent = Intent(this, ParentHomeActivity::class.java)
                myIntent.putExtra("type", "PARENTS") // 다음창으로 값 넘김.
                startActivity(myIntent)
            }
            /*val myIntent = Intent(this, MainActivity::class.java)
                myIntent.putExtra("type", "PARENTS") // 다음창으로 값 넘김.
                startActivity(myIntent)*/
            //println("이름이름 : " + GlobalData.loginUser!!.GetName())
        }
    }
}