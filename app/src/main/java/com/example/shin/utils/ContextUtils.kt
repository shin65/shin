package com.example.shin.utils

import android.content.Context
import com.example.shin.datas.User

class ContextUtils {
    companion object {
        fun setLoginType(context: Context, cookie: String) {
            val pref = context.getSharedPreferences("Woori_Android", Context.MODE_PRIVATE)
            pref.edit().putString("LOGIN_TYPE", cookie).commit()
        }

        fun setUserToken(context: Context, token: String) {
            val pref = context.getSharedPreferences("Woori_Android", Context.MODE_PRIVATE)
            pref.edit().putString("USER_TOKEN", token).commit()
        }

        fun setLoginUser(context: Context, loginUser: User) {
            val pref = context.getSharedPreferences("Woori_Android", Context.MODE_PRIVATE)
            pref.edit().putInt("USER_ID", loginUser.GetId()).commit()
            pref.edit().putString("USER_NAME", loginUser.GetName()).commit()
            pref.edit().putString("USER_PHONE", loginUser.GetPhone_num()).commit()
        }

        fun getUserToken(context: Context): String? {
            val pref = context.getSharedPreferences("Woori_Android", Context.MODE_PRIVATE)
            return pref.getString("USER_TOKEN", "")
        }

    }
}