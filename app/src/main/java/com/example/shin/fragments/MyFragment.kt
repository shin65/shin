package com.example.shin.fragments


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.shin.EditParentProfileActivity
import com.example.shin.ParentProfileActivity
import com.example.shin.R
import kotlinx.android.synthetic.main.fragment_my.*

/**
 * A simple [Fragment] subclass.
 */
class MyFragment : Fragment() {
    //var mContext: Context? = null     //activity 로 대체가능(fragment 에서)

    lateinit var bt_edit : TextView // 해당 fragment view에서 해당 id값을 가진 textview

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val linearLayout : LinearLayout =
            inflater.inflate(R.layout.fragment_my, container, false) as LinearLayout    // 화면에 사용할 view 선언
        bt_edit = linearLayout.findViewById(R.id.bt_edit)   // textview id값 선언

        bt_edit.setOnClickListener {
            val intent = Intent(activity, EditParentProfileActivity::class.java)    // fragment에서는 this 못씀
            startActivityForResult(intent,1)
        }
        // Inflate the layout for this fragment
        return linearLayout

        /*//mContext = context

        val linearLayout : LinearLayout =
            inflater.inflate(R.layout.fragment_my, container, false) as LinearLayout

        bt_edit = linearLayout.findViewById(R.id.bt_edit)

        bt_edit.setOnClickListener(View.OnClickListener {
            val intent = Intent(mContext, ParentProfileActivity::class.java)
            startActivity(intent)
        })
        return inflater.inflate(R.layout.fragment_my, container, false)*/
    }


}
