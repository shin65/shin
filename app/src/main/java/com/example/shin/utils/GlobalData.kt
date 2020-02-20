package com.example.shin.utils


import com.example.shin.datas.DayOfMonth
import com.example.shin.datas.SchoolQuarter
import com.example.shin.datas.User
import java.util.*

object GlobalData {
    var loginUser: User? = null
    var quarter: SchoolQuarter? = null
    var dayOfMonthList: List<DayOfMonth> = ArrayList<DayOfMonth>()
}
