package com.example.shin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.shin.adapters.SchoolSpinnerAdapter
import com.example.shin.datas.School
import com.example.shin.utils.ConnectServer
import kotlinx.android.synthetic.main.activity_add_child.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.bt_back
import org.json.JSONException
import org.json.JSONObject
import java.util.ArrayList

class AddChildActivity : AppCompatActivity() {
    internal lateinit var adapter: SchoolSpinnerAdapter
    internal var list: MutableList<School> = ArrayList<School>()
    val mContext: Context = this



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_child)

        supportActionBar?.hide()

        bt_back.setOnClickListener{
            var myIntent = Intent(this, MainActivity::class.java)
            startActivity(myIntent)}

        //getSchoolList()
    }

    fun getSchoolList() {
        ConnectServer.getRequestSchoolList(
            mContext,
            "",
            object : ConnectServer.JsonResponseHandler {
                override fun onResponse(json: JSONObject) {
                    try {
                        if (json.getInt("code") == 200) {
                            val school = json.getJSONObject("data").getJSONArray("school")

                            list.clear()

                            val first = School()
                            first.SetId(-1)
                            first.SetName("선택해주세요")
                            list.add(first)

                            for (i in 0 until school.length()) {
                                list.add(School.getSchoolFromJson(school.getJSONObject(i)))
                            }

                            runOnUiThread {
                                adapter = SchoolSpinnerAdapter(mContext, list)
                                spinnerLoginParentInfoSchool.setAdapter(adapter)
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