package com.example.shin

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.shin.utils.ConnectServer
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class MainActivity : AppCompatActivity() {
    val mContext: Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        bt_back.setOnClickListener{
            var myIntent = Intent(this, SecondActivity::class.java)
            startActivity(myIntent)}


        bt_auth.setOnClickListener{

            ConnectServer.postRequestPhoneAuth(ph_num.text.toString())

        }

        bt_login.setOnClickListener(){
            var intent = Intent(this, AddChildActivity::class.java)
            ConnectServer.postRequestLogin(ph_num.text.toString(), auth_num.text.toString(), "PARENTS",
                object :ConnectServer.JsonResponseHandler {
                    override fun onResponse(json: JSONObject) {
                        try {
                            if (json.getInt("code") == 200) {
                                //val token = json.getJSONObject("data").getString("token")
                                //ContextUtils.setUserToken(mContext, token)

                                //val user =
                                //    User.getUserFromJson(json.getJSONObject("data").getJSONObject("user"))
                                //ContextUtils.setLoginUser(mContext, user)
                                //GlobalData.loginUser = user
                                println("dddddddddddd")
                                runOnUiThread {

                                    startActivity(intent)
                                    /*
                                    var intent: Intent? = null
                                    if (user.getChild() != null) {
                                        //                                                    intent = new Intent(mContext, ParentHomeActivity.class);
                                        intent = Intent(mContext, ParentTabHomeActivity::class.java)
                                        intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                        startActivity(intent)
                                        finish()
                                    } else {
                                        intent = Intent(mContext, LoginParentInfoActivity::class.java)
                                        startActivity(intent)
                                    }*/
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
