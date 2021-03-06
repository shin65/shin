package com.example.shin

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.example.shin.datas.User
import com.example.shin.fcm.FCMIDService
import com.example.shin.utils.ConnectServer
import com.example.shin.utils.ContextUtils
import com.example.shin.utils.GlobalData
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class MainActivity : AppCompatActivity() {
    val mContext: Context = this // 현재 창 선언.
    lateinit var type : String // 이전 창에서 넘겨 받은 값 담을 변수선언.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        type = intent.getStringExtra("type") // 이전 창에서 넘겨 받은 값으로 초기값 설정.
        //println(type)

        bt_back.setOnClickListener{
            var myIntent = Intent(this, SecondActivity::class.java)
            startActivity(myIntent)}


        bt_auth.setOnClickListener {
            val device_token : String? = FirebaseInstanceId.getInstance().token // 기기 토큰 값
            // app, project gradle에 추가, app gradle에 apply plugin 추가, manifests에 추가.
            if (TextUtils.isEmpty(device_token)) {
                Log.d("token", "token is empty")
                val intent = Intent(mContext, FCMIDService::class.java)
                startService(intent)
            } // <-- 이 코드 넣고 인증번호 발송하면 앱 종료 : Tools에서 Firebase 연동하면 됨 -> 정상작동.
            println("device_token : " + device_token)
            ConnectServer.postRequestPhoneAuth(     // EditText에서 받은 값 서버 api 호출.
                mContext,
                ph_num.text.toString(),
                device_token,
                object : ConnectServer.JsonResponseHandler {
                    override fun onResponse(json: JSONObject) {
                        try {
                            if (json.getInt("code") == 200) {
                                runOnUiThread {
                                    Toast.makeText(
                                        mContext,
                                        "인증번호가 발송되었습니다.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            } else {
                                val message = json.getString("message")
                                runOnUiThread {
                                    Toast.makeText(
                                        mContext,
                                        message,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }

                    }
                })
            }
        bt_login.setOnClickListener(){
            //var intent = Intent(this, AddChildActivity::class.java)
            //startActivity(intent)

            ContextUtils.setLoginType(mContext, type)   // 기기에 로그인 type 저장. (ex. PARENTS).

            when(type){
                "PARENTS" -> ConnectServer.postRequestLogin(ph_num.text.toString(), auth_num.text.toString(), type,
                    object :ConnectServer.JsonResponseHandler {     // 인증번호 검사 후 로그인.
                        override fun onResponse(json: JSONObject) {
                            try {
                                if (json.getInt("code") == 200) {
                                    //Log.d("log", json.toString())
                                    val token = json.getJSONObject("data").getString("token")
                                    ContextUtils.setUserToken(mContext, token)

                                    val user =
                                        User.getUserFromJson(json.getJSONObject("data").getJSONObject("user"))
                                    ContextUtils.setLoginUser(mContext, user)
                                    GlobalData.loginUser = user
                                    //println("dddddddddddd")
                                    runOnUiThread {

                                        //startActivity(intent)

                                        //var intent: Intent? = null

                                        println("이름 : " + user.GetName())
                                        /*if(user.GetName() != null) {
                                            println("이름이 없다")
                                            var intent = Intent(mContext, ParentProfileActivity::class.java)
                                            //intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                            startActivity(intent)
                                            //finish()
                                        }*/
                                        if (user.GetChild() != null) {      // client의 자녀 정보확인후 연결 창.
                                            //                                                    intent = new Intent(mContext, ParentHomeActivity.class);
                                            println("자녀가 있습니다.")
                                            var intent = Intent(mContext, ParentProfileActivity::class.java)
                                            intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                            startActivity(intent)
                                            finish()
                                        } else {
                                            println("자녀가 없습니다.")
                                            var intent = Intent(mContext, AddChildActivity::class.java)
                                            startActivity(intent)
                                        }
                                    }
                                } else {
                                    val message = json.getString("message")
                                    runOnUiThread {
                                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                                    }
                                }
                            } catch (e: JSONException) {
                                e.printStackTrace()
                            }

                        }

                    } )
            }




        }

    }
}
