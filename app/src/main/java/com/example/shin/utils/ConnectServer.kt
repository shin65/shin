package com.example.shin.utils

import android.content.Context
import android.net.ConnectivityManager
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
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
    fun checkIntenetSetting(context: Context): Boolean {
        var isConnected = false

        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        val wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)


        // wifi 또는 모바일 네트워크 어느 하나라도 연결이 되어있다면,
        if (wifi!!.isConnected || mobile!!.isConnected) {
            Log.i("연결됨", "연결이 되었습니다.")
            isConnected = true
        } else {
            Log.i("연결 안 됨", "연결이 다시 한번 확인해주세요")
            val mHandler = Handler(Looper.getMainLooper())
            mHandler.postDelayed({
                Toast.makeText(context, "인터넷 연결을 확인해주세요.", Toast.LENGTH_SHORT).show()
            }, 0)
            isConnected = false
        }

        return isConnected
    }

    fun postRequestPhoneAuth(context:Context, ph_num: String, device_token : String, handler: JsonResponseHandler) {
        val client = OkHttpClient()

        val requestBody = FormBody.Builder()
            .add("phone_num", ph_num)
            .add("device_token", device_token)
            .add("os", "iOS")
            .build()

        val request = Request.Builder()
            .header("X-Http-Token", ContextUtils.getUserToken(context).toString())
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
                    if (handler != null)
                        handler.onResponse(json)
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

    fun getRequestSchoolList(context: Context, name: String, handler: JsonResponseHandler) {
        if (!checkIntenetSetting(context)) {
            return
        }

        val client = OkHttpClient()

        val urlBuilder = HttpUrl.parse("http://ec2-52-78-148-252.ap-northeast-2.compute.amazonaws.com/school")!!.newBuilder()
        urlBuilder.addEncodedQueryParameter("name", name)

        val requestUrl = urlBuilder
            .build().toString()

        val request = Request.Builder()
            //                .header("X-Http-Token" , ContextUtils.getUserToken(context))
            .url(requestUrl)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("error", "Connect Server Error is $e")
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
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