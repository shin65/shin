package com.example.shin.datas

import org.json.JSONException
import org.json.JSONObject
import java.io.Serializable
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class SchoolQuarter : Serializable {
    var id = 0
    var school_id = 0
    var years = 0
    var type: String? = null
    var quarter: String? = null
    var start_date: Calendar? = null
    var end_date: Calendar? = null
    var confirm_start_date: Calendar? = null
    var confirm_end_date: Calendar? = null
    var reading_start_date: Calendar? = null
    var reading_end_date: Calendar? = null
    var apply_start_date: Calendar? = null
    var apply_end_date: Calendar? = null
    var winner_date: Calendar? = null
    var wait_push_date: Calendar? = null
    var wait_push_end_date: Calendar? = null
    var wait_term_minute = 0
    var file_url: String? = null
    var created_at: Calendar? = null
    var updated_at: Calendar? = null
    //var lecture_class: MutableList<LectureClass> = ArrayList<LectureClass>()


    companion object {
        fun getQuarterFromJson(json: JSONObject): SchoolQuarter {
            val quarter = SchoolQuarter()
            try {
                if (!json.isNull("id")) {
                    quarter.id = json.getInt("id")
                }
                if (!json.isNull("quarter")) {
                    quarter.quarter = json.getString("quarter")
                }
                if (!json.isNull("years")) {
                    quarter.years = json.getInt("years")
                }
                if (!json.isNull("type")) {
                    quarter.type = json.getString("type")
                }
                if (!json.isNull("file_url")) {
                    quarter.file_url = json.getString("file_url")
                }
                val myDateTimeFormat =
                    SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                if (!json.isNull("start_date")) {
                    val start_date = Calendar.getInstance()
                    start_date.time = myDateTimeFormat.parse(json.getString("start_date"))
                    quarter.start_date = start_date
                }
                if (!json.isNull("end_date")) {
                    val end_date = Calendar.getInstance()
                    end_date.time = myDateTimeFormat.parse(json.getString("end_date"))
                    quarter.end_date = end_date
                }
                if (!json.isNull("apply_start_date")) {
                    val apply_start_date = Calendar.getInstance()
                    apply_start_date.time =
                        myDateTimeFormat.parse(json.getString("apply_start_date"))
                    quarter.apply_start_date = apply_start_date
                }
                if (!json.isNull("apply_end_date")) {
                    val apply_end_date = Calendar.getInstance()
                    apply_end_date.time = myDateTimeFormat.parse(json.getString("apply_end_date"))
                    quarter.apply_end_date = apply_end_date
                }
                if (!json.isNull("winner_date")) {
                    val winner_date = Calendar.getInstance()
                    winner_date.time = myDateTimeFormat.parse(json.getString("winner_date"))
                    quarter.winner_date = winner_date
                }
                if (!json.isNull("wait_push_date")) {
                    val wait_push_date = Calendar.getInstance()
                    wait_push_date.time = myDateTimeFormat.parse(json.getString("wait_push_date"))
                    quarter.wait_push_date = wait_push_date
                }
                if (!json.isNull("wait_push_end_date")) {
                    val wait_push_end_date = Calendar.getInstance()
                    wait_push_end_date.time =
                        myDateTimeFormat.parse(json.getString("wait_push_end_date"))
                    quarter.wait_push_end_date = wait_push_end_date
                }
                /*if (!json.isNull("lecture_class")) {
                    quarter.getLecture_class().clear()
                    val lecture_class = json.getJSONArray("lecture_class")
                    for (i in 0 until lecture_class.length()) {
                        quarter.getLecture_class()
                            .add(LectureClass.getClassFromJson(lecture_class.getJSONObject(i)))
                    }
                }*/
            } catch (e: JSONException) {
                e.printStackTrace()
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return quarter
        }
    }

    fun SchoolQuarter() {}

    @JvmName("getid")
    fun getId(): Int {
        return id
    }

    @JvmName("setid")
    fun setId(id: Int) {
        this.id = id
    }

    @JvmName("getschoolid")
    fun getSchool_id(): Int {
        return school_id
    }

    @JvmName("setschoolid")
    fun setSchool_id(school_id: Int) {
        this.school_id = school_id
    }

    @JvmName("getyears")
    fun getYears(): Int {
        return years
    }

    @JvmName("setyears")
    fun setYears(years: Int) {
        this.years = years
    }

    @JvmName("gettype")
    fun getType(): String? {
        return type
    }

    @JvmName("settype")
    fun setType(type: String?) {
        this.type = type
    }

    @JvmName("getquater")
    fun getQuarter(): String? {
        return quarter
    }

    @JvmName("setquater")
    fun setQuarter(quarter: String?) {
        this.quarter = quarter
    }

    @JvmName("getstart")
    fun getStart_date(): Calendar? {
        return start_date
    }

    @JvmName("setstartdate")
    fun setStart_date(start_date: Calendar?) {
        this.start_date = start_date
    }

    @JvmName("getenddate")
    fun getEnd_date(): Calendar? {
        return end_date
    }

    @JvmName("setenddate")
    fun setEnd_date(end_date: Calendar?) {
        this.end_date = end_date
    }

    @JvmName("getconfirmstartdate")
    fun getConfirm_start_date(): Calendar? {
        return confirm_start_date
    }

    @JvmName("setconfirmstartdate")
    fun setConfirm_start_date(confirm_start_date: Calendar?) {
        this.confirm_start_date = confirm_start_date
    }

    @JvmName("getconfirmenddate")
    fun getConfirm_end_date(): Calendar? {
        return confirm_end_date
    }

    @JvmName("setconfirmenddate")
    fun setConfirm_end_date(confirm_end_date: Calendar?) {
        this.confirm_end_date = confirm_end_date
    }

    @JvmName("detreadingstartdate")
    fun getReading_start_date(): Calendar? {
        return reading_start_date
    }

    @JvmName("setreadingstartdate")
    fun setReading_start_date(reading_start_date: Calendar?) {
        this.reading_start_date = reading_start_date
    }

    @JvmName("getreadingenddate")
    fun getReading_end_date(): Calendar? {
        return reading_end_date
    }

    @JvmName("setreadingenddate")
    fun setReading_end_date(reading_end_date: Calendar?) {
        this.reading_end_date = reading_end_date
    }

    @JvmName("getapplystartdate")
    fun getApply_start_date(): Calendar? {
        return apply_start_date
    }

    @JvmName("setapplystartdate")
    fun setApply_start_date(apply_start_date: Calendar?) {
        this.apply_start_date = apply_start_date
    }

    @JvmName("getapplyenddate")
    fun getApply_end_date(): Calendar? {
        return apply_end_date
    }

    @JvmName("setapplyenddate")
    fun setApply_end_date(apply_end_date: Calendar?) {
        this.apply_end_date = apply_end_date
    }

    @JvmName("getwinnerdate")
    fun getWinner_date(): Calendar? {
        return winner_date
    }

    @JvmName("setwinnerdate")
    fun setWinner_date(winner_date: Calendar?) {
        this.winner_date = winner_date
    }

    @JvmName("getwaitpushdate")
    fun getWait_push_date(): Calendar? {
        return wait_push_date
    }

    @JvmName("setwaitpushdate")
    fun setWait_push_date(wait_push_date: Calendar?) {
        this.wait_push_date = wait_push_date
    }

    @JvmName("getwaitpushenddate")
    fun getWait_push_end_date(): Calendar? {
        return wait_push_end_date
    }

    @JvmName("setwaitpushenddate")
    fun setWait_push_end_date(wait_push_end_date: Calendar?) {
        this.wait_push_end_date = wait_push_end_date
    }

    @JvmName("getwaittermminute")
    fun getWait_term_minute(): Int {
        return wait_term_minute
    }

    @JvmName("setwaittermminute")
    fun setWait_term_minute(wait_term_minute: Int) {
        this.wait_term_minute = wait_term_minute
    }

    @JvmName("getfileurl")
    fun getFile_url(): String? {
        return file_url
    }

    @JvmName("setfileurl")
    fun setFile_url(file_url: String?) {
        this.file_url = file_url
    }

    @JvmName("getcreatedat")
    fun getCreated_at(): Calendar? {
        return created_at
    }

    @JvmName("setcreatedat")
    fun setCreated_at(created_at: Calendar?) {
        this.created_at = created_at
    }

    @JvmName("getupdatedat")
    fun getUpdated_at(): Calendar? {
        return updated_at
    }

    @JvmName("setupdatedat")
    fun setUpdated_at(updated_at: Calendar?) {
        this.updated_at = updated_at
    }

    /*@JvmName("getlectureclass")
    fun getLecture_class(): List<LectureClass?>? {
        return lecture_class
    }

    @JvmName("setlectureclass")
    fun setLecture_class(lecture_class: List<LectureClass?>?) {
        this.lecture_class = lecture_class as MutableList<Any>
    }*/
}