package com.example.shin.fragments


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.shin.ApplyClassActivity
import com.example.shin.R
import com.example.shin.datas.SchoolQuarter
import com.example.shin.utils.ConnectServer
import com.example.shin.utils.GlobalData
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class AfterSchool : Fragment() {

    var mContext : Context? = null

    var schoolQuarter: SchoolQuarter? = null
    var myDateFormat = SimpleDateFormat("yyyy.MM.dd")

    lateinit var bt_afterschool_apply : LinearLayout
    lateinit var txtvAfterSchoolApplyAvailable : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val linearLayout : LinearLayout =
            inflater.inflate(R.layout.fragment_after_school, container, false) as LinearLayout

        mContext = getContext()

        bt_afterschool_apply = linearLayout.findViewById(R.id.bt_afterschool_apply)
        txtvAfterSchoolApplyAvailable = linearLayout.findViewById(R.id.txtvAfterSchoolApplyAvailable)

        bt_afterschool_apply.setOnClickListener {
            val intent = Intent(activity, ApplyClassActivity::class.java)    // fragment에서는 this 못씀
            startActivityForResult(intent,1)
        }

        getSchoolQuarter()
        // Inflate the layout for this fragment
        return linearLayout
    }

    fun getSchoolQuarter() {
        ConnectServer.getRequestSchoolQuarterList(mContext, object : ConnectServer.JsonResponseHandler {
            override fun onResponse(json: JSONObject) {
                try {
                    if (json.getInt("code") == 200) {
                        schoolQuarter = SchoolQuarter.getQuarterFromJson(
                            json.getJSONObject("data").getJSONObject("school_quarter")
                        )
                        GlobalData.quarter = schoolQuarter
                        activity!!.runOnUiThread {

                            if (Calendar.getInstance().timeInMillis < schoolQuarter!!.getApply_end_date()!!.timeInMillis) {
                                txtvAfterSchoolApplyAvailable.visibility = View.VISIBLE
                                txtvAfterSchoolApplyAvailable.text = String.format(
                                    "%s (%s 까지)",
                                    "지금은 수강신청 기간입니다."
                                    ,
                                    myDateFormat.format(schoolQuarter!!.getApply_end_date()!!.time)
                                )
                            } else {
                                txtvAfterSchoolApplyAvailable.visibility = View.GONE
                            }

                        }
                    } else {
                        val message = json.getString("message")
                        activity!!.runOnUiThread {
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
    }
}
