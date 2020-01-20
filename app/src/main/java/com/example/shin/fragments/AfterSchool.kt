package com.example.shin.fragments


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.shin.ApplyClassActivity

import com.example.shin.R

/**
 * A simple [Fragment] subclass.
 */
class AfterSchool : Fragment() {

    lateinit var bt_afterschool_apply : LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val linearLayout : LinearLayout =
            inflater.inflate(R.layout.fragment_after_school, container, false) as LinearLayout
        bt_afterschool_apply = linearLayout.findViewById(R.id.bt_afterschool_apply)

        bt_afterschool_apply.setOnClickListener {
            val intent = Intent(activity, ApplyClassActivity::class.java)    // fragment에서는 this 못씀
            startActivityForResult(intent,1)
        }

        // Inflate the layout for this fragment
        return linearLayout
    }


}
