package com.example.shin.utils

import android.content.Context
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class ConnectServer {

    interface JsonResponseHandler {
        fun onResponse(json: JSONObject)
    }
companion object {
    fun postRequestPhoneAuth(ph_num: String) {
        val client = OkHttpClient()

        val requestBody = FormBody.Builder()
            .add("phone_num", ph_num)
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
                    // val json = JSONObject(body)
                    //if (handler != null)
                    //    handler.onResponse(json)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            }
        })
    }

    fun postRequestLogin(
        phone_num: String,
        phone_auth_num: String,
        type: String,
        handler: JsonResponseHandler
    ) {
        /*if (!checkIntenetSetting(context)) {
            return
        }*/

        val client = OkHttpClient()

        //Request Body에 서버에 보낼 데이터 작성
        val requestBody = FormBody.Builder()
            .add("phone_num", phone_num)
            .add("phone_auth_num", phone_auth_num)
            .add("type", type)
            .build()

        //작성한 Request Body와 데이터를 보낼 url을 Request에 붙임
        val request = Request.Builder()
            .url("http://ec2-52-78-148-252.ap-northeast-2.compute.amazonaws.com/auth")
            .post(requestBody)
            .build()

        //request를 Client에 세팅하고 Server로 부터 온 Response를 처리할 Callback 작성
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("error", "Connect Server Error is $e")
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                //                Log.d("aaaa", "Response Body is " + response.body().string());
                val body = response.body()!!.string()
                Log.d("log", "서버에서 응답한 Body:$body")
                try {
                    val json = JSONObject(body)
                    handler?.onResponse(json)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            }
        })
    }
}
}