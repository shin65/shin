package com.example.shin.datas

import org.json.JSONException
import org.json.JSONObject

import java.io.Serializable
import java.util.Calendar


class StudentInfo : Serializable {
    var id: Int = 0
    var student_id: Int = 0
    var years: Int = 0
    var school_id: Int = 0
    var grade: Int = 0
    var class_number: Int = 0
    var number: Int = 0
    var created_at: Calendar? = null
    var updated_at: Calendar? = null

    companion object {

        fun getInfoFromJson(json: JSONObject): StudentInfo {
            val info = StudentInfo()

            try {
                info.id = json.getInt("id")
                info.years = json.getInt("years")
                info.grade = json.getInt("grade")
                info.class_number = json.getInt("class_number")
                info.number = json.getInt("number")
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            return info
        }
    }
}