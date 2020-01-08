package com.example.shin

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.shin.utils.ConnectServer
import com.example.shin.utils.ContextUtils
import com.example.shin.utils.GlobalData
import kotlinx.android.synthetic.main.activity_terms.*
import org.json.JSONException
import org.json.JSONObject

class TermsActivity :AppCompatActivity() {
    val mContext: Context = this // 현재 창 선언.

    private var flagCheckAll = 0
    private var flagCheckUsage = 0
    private var flagCheckPrivateInfo = 0
    private var flagCheckPromotion = 0
    private var goodToAgree = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms)
        supportActionBar?.hide()

        button_terms_arrow_left.setOnClickListener {
            val myIntent = Intent(this, MainActivity::class.java)

            startActivity(myIntent)
        }

        checkAll.setOnClickListener{
            if(flagCheckAll == 0) {
                checkAll.setImageResource(R.drawable.ovalchecked)
                flagCheckAll = 1

                checkUsage.setImageResource(R.drawable.checked)
                flagCheckUsage = 1

                checkPrivateInfo.setImageResource(R.drawable.checked)
                flagCheckPrivateInfo = 1

                checkPromotion.setImageResource(R.drawable.checked)
                flagCheckPromotion = 1

            }

            else {
                checkAll.setImageResource(R.drawable.ovalunchecked)
                flagCheckAll = 0

                checkUsage.setImageResource(R.drawable.unchecked)
                flagCheckUsage = 0

                checkPrivateInfo.setImageResource(R.drawable.unchecked)
                flagCheckPrivateInfo= 0

                checkPromotion.setImageResource(R.drawable.unchecked)
                flagCheckPromotion= 0
            }
            activeAgree()
        }
        checkUsage.setOnClickListener{
            if(flagCheckUsage == 0){
                checkUsage.setImageResource(R.drawable.checked)
                flagCheckUsage = 1
            }
            else{
                checkUsage.setImageResource(R.drawable.unchecked)
                flagCheckUsage = 0
            }
            activeAgree()
        }
        checkPrivateInfo.setOnClickListener{
            if(flagCheckPrivateInfo == 0){
                checkPrivateInfo.setImageResource(R.drawable.checked)
                flagCheckPrivateInfo = 1
            }
            else{
                checkPrivateInfo.setImageResource(R.drawable.unchecked)
                flagCheckPrivateInfo = 0
            }
            activeAgree()
        }
        checkPromotion.setOnClickListener{
            if(flagCheckPromotion == 0){
                checkPromotion.setImageResource(R.drawable.checked)
                flagCheckPromotion = 1
            }
            else{
                checkPromotion.setImageResource(R.drawable.unchecked)
                flagCheckPromotion = 0
            }
            activeAgree()
        }


        agree.setOnClickListener{
            ConnectServer.userService(mContext,flagCheckUsage, flagCheckPrivateInfo, flagCheckPromotion, object: ConnectServer.JsonResponseHandler{
                override fun onResponse(json: JSONObject) {
                    try {
                        if (json.getInt("code") == 200) {
                            Log.d("log", ContextUtils.getUserToken(mContext))

                            runOnUiThread {
                                if (GlobalData.loginUser?.child == null) {
                                    var intent: Intent? = null
                                    intent = Intent(mContext, AddChildActivity::class.java)
                                    startActivity(intent)
                                }
                                else if(GlobalData.loginUser?.profile_image_url == null){
                                    //프로필 등록
                                }
                                else{
                                    //학부모 홈으로
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
            })
        }

    }

    private fun activeAgree() {
        if (flagCheckUsage == 1 && flagCheckPrivateInfo == 1) {
            agree.setBackgroundColor(Color.parseColor("#2cc68f"))
            goodToAgree = 1
        }
        else {
            agree.setBackgroundColor(Color.parseColor("#d1d1d1"))
            goodToAgree = 0
        }
    }




}