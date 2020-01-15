package com.example.shin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.shin.adapters.SchoolSpinnerAdapter
import com.example.shin.datas.School
import com.example.shin.fragments.Home
import com.example.shin.utils.ConnectServer
import com.example.shin.utils.GlobalData
import kotlinx.android.synthetic.main.activity_add_child.*
import kotlinx.android.synthetic.main.activity_main.bt_back
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
//import sun.jvm.hotspot.utilities.IntArray
import java.util.*


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

        bt_complete.setOnClickListener() {
            val selected = spinnerLoginParentInfoSchool.getSelectedItem() as School
            if (selected.GetId() === -1) {
                Toast.makeText(mContext, "학교를 선택해주세요.", Toast.LENGTH_SHORT).show()
            }

            ConnectServer.postRequestRegistChild(mContext,
                edit_name.text.toString(),
                edit_stu_password.text.toString(),
                selected.GetNumber(),
                edit_grade.text.toString(),
                edit_ban.text.toString(),
                edit_bun.text.toString(),
                object : ConnectServer.JsonResponseHandler {
                    override fun onResponse(json: JSONObject) {
                        try {
                            if (json.getInt("code") == 200) {
                                runOnUiThread {
                                    println("신문철")

                                    /*
                                    val fragment: Fragment =
                                        Home() // Fragment 생성

                                    val bundle = Bundle(1) // 파라미터는 전달할 데이터 개수

                                    bundle.putString("name", edit_name.text.toString()) // key , value

                                    fragment.arguments = bundle
                                    */

                                    /*
                                    val intenthome=
                                        Intent(mContext, Home::class.java)
                                    intent.putExtra("name", edit_name.text.toString())
                                     */

                                    val intent =
                                        Intent(mContext, ParentHomeActivity::class.java)
                                    intent.putExtra("name", edit_name.text.toString()) // 다음창으로 값 넘김.
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                    startActivity(intent)
                                    finish()
                                }
                            } else {
                                val message = json.getString("message")
                                runOnUiThread {
                                    Toast.makeText(
                                        mContext,
                                        message,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }
                })
            println("글로벌 : " + GlobalData.loginUser!!.GetName() + GlobalData.loginUser!!.GetPhone_num() + GlobalData.loginUser!!.GetChild()!!.GetName())
        }
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