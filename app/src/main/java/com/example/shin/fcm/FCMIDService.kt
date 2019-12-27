package com.example.shin.fcm

import android.util.Log

import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService

import org.json.JSONObject

class FCMIDService : FirebaseInstanceIdService() {
    override fun onTokenRefresh() {
        val token = FirebaseInstanceId.getInstance().token

        Log.d("token값", token!!)

        //        if (!ContextUtils.getUserToken(this).equals("")) {
        //            Log.d("token", "token is not empty");
        //            ConnectServer.getRequestUserData(this, token, new ConnectServer.JsonResponseHandler() {
        //                @Override
        //                public void onResponse(JSONObject json) {
        //
        //                }
        //            });
        //        }
    }
}
