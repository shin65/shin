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
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.ArrayList

class AddChildActivity : AppCompatActivity() {
    lateinit var adapter: SchoolSpinnerAdapter
    var list: ArrayList<School> = ArrayList<School>()
    val mContext: Context = this



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_child)

        supportActionBar?.hide()

        bt_back.setOnClickListener{
            var myIntent = Intent(this, MainActivity::class.java)
            startActivity(myIntent)}

        getSchoolList()
    }

    private fun getSchoolList() {
        ConnectServer.getRequestSchoolList(
            mContext,
            "",
            object : ConnectServer.JsonResponseHandler {
                override fun onResponse(json: JSONObject) {
                    try {
                        if (json.getInt("code") == 200) {
                            val school : JSONArray = json.getJSONObject("data").getJSONArray("school")
println("학교 : " + school)
                            list.clear()

                            val first : School = School()
                            first.SetId(-1)
                            first.SetName("선택해주세요")
                            list.add(first)
println("선택해주세요 리스트 목록 : " + list + first.GetName() + school.length() + list[0].GetName() + "--" + school.getJSONObject(0))
                            for (i in 0 until school.length()) {
                                list.add(School.getSchoolFromJson(school.getJSONObject(i)))
                            }
println("여기까지 이상무!")
                            println("리스트 목록 : " + list[1].GetName() + list[1]) // list에 담겨진 데이터 형태 잘못된것 같음.
                            runOnUiThread {
                                adapter = SchoolSpinnerAdapter(mContext, list) // <----------------------------------------------
                                spinnerLoginParentInfoSchool.setAdapter(adapter)
                                println("어댑터 값 : " + adapter.getItem(1))
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