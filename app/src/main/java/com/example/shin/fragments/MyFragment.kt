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
import com.example.shin.ParentProfileActivity
import com.example.shin.R
import kotlinx.android.synthetic.main.fragment_my.*

/**
 * A simple [Fragment] subclass.
 */
class MyFragment : Fragment() {
    var mContext: Context? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my, container, false)

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
