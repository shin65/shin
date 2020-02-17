package com.example.shin

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Gallery
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.shin.utils.ConnectServer
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_parent_profile.*
import org.json.JSONException
import org.json.JSONObject

class ParentProfileActivity : AppCompatActivity() {     // 프로필 사진, 이메일등록 미완
    val Gallery = 0

    val mContext: Context = this // 현재 창 선언.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parent_profile)

        supportActionBar?.hide() // 상단 바 감추기


        //intent.type = "image/*"
        //intent.action = Intent.ACTION_GET_CONTENT
        //startActivityForResult(Intent.createChooser(intent,"Load Picture"), Gallery) // 갤러라애서 이미지 파일 등록시키는 함수 호출

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
            var intent = Intent(mContext, ParentHomeActivity::class.java) // AddChildActivity -> ParentHomeActivity 임시 변경
            startActivity(intent)
        }
    }

    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == Gallery){
            if(resultCode == RESULT_OK){
                var dataUri = data?.data
                try{
                    var bitmap : Bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, dataUri)
                    //tempBitmap = bitmap
                    imageView.setImageBitmap(bitmap)
                } catch (e:Exception){
                    Toast.makeText(this, "$e",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }*/
}