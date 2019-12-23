package com.example.shin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
//import com.example.myapplication.utils.ConnectServer
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        bt_back.setOnClickListener{
            var myIntent = Intent(this, SecondActivity::class.java)
            startActivity(myIntent)}



        bt_auth.setOnClickListener{

        val client = OkHttpClient()

        val requestBody = FormBody.Builder()
            .add("phone_num", ph_num.text.toString())
            .add("device_token", "1")
            .add("os", "iOS")
            .build()

        val request = Request.Builder()
            //.header("X-Http-Token", ContextUtils.getUserToken(context))
            .url("http://ec2-52-78-148-252.ap-northeast-2.compute.amazonaws.com/" + "phone_auth")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("error!!")

            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                //                Log.d("aaaa", "Response Body is " + response.body().string());
                val body = response.body()!!.string()
                Log.d("log", "서버에서 응답한 Body:$body")
                try {
                    val json = JSONObject(body)
                    //if (handler != null)
                    //    handler.onResponse(json)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            }
        })

        }

    }
}
