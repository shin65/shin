package com.example.shin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.shin.utils.ConnectServer
import com.gun0912.tedpermission.PermissionListener
import kotlinx.android.synthetic.main.activity_edit_parent_profile.*
import kotlinx.android.synthetic.main.activity_parent_profile.*
import kotlinx.android.synthetic.main.activity_parent_profile.bt_complete
import kotlinx.android.synthetic.main.activity_parent_profile.bt_name
import org.json.JSONException
import org.json.JSONObject

class EditParentProfileActivity : AppCompatActivity() {     // 프로필 사진, 이메일등록 미완
    val Gallery = 0

    val mContext: Context = this // 현재 창 선언.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_parent_profile)

        supportActionBar?.hide() // 상단 바 감추기

        //tedPermission() // 갤러리, 카메라 사진 요청 함수 호출

        bt_complete.setOnClickListener(){                // 아직 이미지파일을 파일화 시켜서 api 호출 못한 상태 !!!
            ConnectServer.postRequestProfileLogin(     // EditText에서 받은 값 서버 api 호출.
                mContext,
                bt_name.text.toString(),
                object : ConnectServer.JsonResponseHandler {
                    override fun onResponse(json: JSONObject) {
                        try {
                            if (json.getInt("code") == 200) {
                                runOnUiThread {
                                    Toast.makeText(
                                        mContext,
                                        "프로필 등록 완료",
                                        Toast.LENGTH_SHORT
                                    ).show()
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
            // 완료시 뒤로가기 => 다시 more fragment 로 이동
            //var intent = Intent(mContext, AddChildActivity::class.java)
            //startActivity(intent)
            finish()
        }

        bt_edit_back.setOnClickListener{
            finish()
        }
    }

    /*fun tedPermission() {
        val permissionListener : PermissionListener {

        }
    }*/

}