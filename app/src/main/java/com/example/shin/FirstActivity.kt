package com.example.shin

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.example.shin.datas.User
import com.example.shin.utils.ConnectServer
import com.example.shin.utils.GlobalData
import com.google.firebase.iid.FirebaseInstanceId
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*


class FirstActivity : AppCompatActivity() {
    val mContext : Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activiry_first)

        supportActionBar?.hide() // 상단 바 감추기

        val myDateFormat = SimpleDateFormat("yyyy-MM-dd")
        getParentInfo(myDateFormat.format(Calendar.getInstance().time))

    }

    private fun getParentInfo(date: String?) {
        Log.d("date", date)
        val device_token = FirebaseInstanceId.getInstance().token
        if (TextUtils.isEmpty(device_token)) {
            Log.d("token", "token is empty")

            //val intent = Intent(mContext, FCMIDService::class.java)
            //startService(intent)
        }
        ConnectServer.getRequestParentInfo(mContext, date, device_token, object : ConnectServer.JsonResponseHandler {
            override fun onResponse(json: JSONObject) {
                try {
                    if (json.getInt("code") == 200) {
                        val user: User = User.getUserFromJson(json.getJSONObject("data").getJSONObject("user"))
                        println("UserCheck")
                        println(user.id)

                        //ContextUtils.setLoginUser(mContext, user)
                        GlobalData.loginUser = user
                        println("GlobalDataCheck")
                        println(GlobalData.loginUser?.terms_time.toString())
                        runOnUiThread {
                            var intent: Intent? = null
                            val message = json.getString("message")
                            runOnUiThread {
                                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                                val intent =
                                    Intent(mContext, SecondActivity::class.java)
                                //startActivity(intent)
                                Handler().postDelayed({
                                    startActivity(intent)
                                },1500) // 자동 화면 넘김.
                                //finish()
                            }
                        }
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                    runOnUiThread {
                        val intent = Intent(mContext, SecondActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        })
    }
}
