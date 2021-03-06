package com.example.shin.utils

import android.content.Context
import android.net.ConnectivityManager
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class ConnectServer {

    interface JsonResponseHandler {
        fun onResponse(json: JSONObject)
    }

companion object {
    fun checkIntenetSetting(context: Context): Boolean {    // 인터넷 연결 여부 검사.
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

    fun postRequestPhoneAuth(context:Context, ph_num: String, device_token : String?, handler: JsonResponseHandler) {   // 서버 api 인증번호 요청.
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

    fun postRequestLogin(   // 인증번호 일치 확인 후 로그인.
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
            .url("http://172.30.1.57:5000/auth") // localhost Api 요청, 내부ip로접속
            //.url("http://ec2-52-78-148-252.ap-northeast-2.compute.amazonaws.com/auth")
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

    fun postRequestProfileLogin(context:Context, name: String?, handler: JsonResponseHandler) {
        val client = OkHttpClient()

        val requestBody = FormBody.Builder()
            .add("name", name)
            //.add("image", image)
            .build()

        val request = Request.Builder()
            .header("X-Http-Token", ContextUtils.getUserToken(context).toString())
            .url("http://172.30.1.57:5000/user_info")   // url에 http:// 안쓰면 Error
            //.url("http://ec2-52-78-148-252.ap-northeast-2.compute.amazonaws.com/" + "user_info")
            .put(requestBody)
            .build()

        println("토큰 값: " + ContextUtils.getUserToken(context).toString())

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

    fun postRequestRegistChild(
        context: Context?,
        name: String?,
        password: String?,
        school_number: String?,
        grade: String?,
        class_number: String?,
        number: String?,
        handler: JsonResponseHandler?
    ) {
        if (!checkIntenetSetting(context!!)) {
            return
        }
        val client = OkHttpClient()
        //Request Body에 서버에 보낼 데이터 작성
        val requestBody: RequestBody = FormBody.Builder()
            .add("name", name)
            .add("password", password)
            .add("school_number", school_number)
            .add("grade", grade)
            .add("class_number", class_number)
            .add("number", number)
            .build()
        //작성한 Request Body와 데이터를 보낼 url을 Request에 붙임
        val request: Request = Request.Builder()
            .header("X-Http-Token", ContextUtils.getUserToken(context))
            .url("http://ec2-52-78-148-252.ap-northeast-2.compute.amazonaws.com/parent_student")
            .post(requestBody)
            .build()
        //request를 Client에 세팅하고 Server로 부터 온 Response를 처리할 Callback 작성
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("error", "Connect Server Error is $e")
            }

            @Throws(IOException::class)
            override fun onResponse(
                call: Call,
                response: Response
            ) { //                Log.d("aaaa", "Response Body is " + response.body().string());
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

    fun getRequestParentInfo(context: Context?, date: String?, token: String?, handler: JsonResponseHandler?) {
//            if (! ConnectServer.checkIntenetSetting(context)) {
//                return
//            }
        val client = OkHttpClient()
        val urlBuilder =
            HttpUrl.parse("http://ec2-52-78-148-252.ap-northeast-2.compute.amazonaws.com/me_info") !!.newBuilder()
        if (date != "") {
            urlBuilder.addEncodedQueryParameter("date", date)
        }
        urlBuilder.addEncodedQueryParameter("device_token", token)
        urlBuilder.addEncodedQueryParameter("os", "Android")
        val requestUrl = urlBuilder.build().toString()
        val request = Request.Builder()
            .header("X-Http-Token", ContextUtils.getUserToken(context !!))
            .url(requestUrl)
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("error", "Connect Server Error is $e")
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val body = response.body() !!.string()
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

    fun userService(context: Context, terms: Int, privacy: Int, notify_msg:Int, handler: JsonResponseHandler){
        val client = OkHttpClient()

        val requestBody: RequestBody = FormBody.Builder()
            .add("terms", terms.toString())
            .add("privacy", privacy.toString())
            .add("notify_msg", notify_msg.toString())
            .build()
        //작성한 Request Body와 데이터를 보낼 url을 Request에 붙임
        val request = Request.Builder()
            .header("X-Http-Token", ContextUtils.getUserToken(context))
            .url("http://ec2-52-78-148-252.ap-northeast-2.compute.amazonaws.com/user_service")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("error", "Connect Server Error is $e")
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                //                Log.d("aaaa", "Response Body is " + response.body().string());
                val body = response.body()?.string()
                Log.d("log", "서버에서 응답한 Body:$body")
                try {
                    val json = JSONObject(body)
                    if (handler != null) {
                        handler.onResponse(json)
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        })
    }

    fun getRequestSchoolQuarterList(
        context: Context?,
        handler: JsonResponseHandler?
    ) {
        if (!checkIntenetSetting(context!!)) {
            return
        }
        val client = OkHttpClient()
        val urlBuilder =
            HttpUrl.parse("http://172.30.1.57:5000/school_quarter_info")!!.newBuilder()
            //HttpUrl.parse("http://ec2-52-78-148-252.ap-northeast-2.compute.amazonaws.com/school_quarter_info")!!.newBuilder()
        val requestUrl = urlBuilder
            .build().toString()
        val request = Request.Builder()
            .header("X-Http-Token", ContextUtils.getUserToken(context))
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