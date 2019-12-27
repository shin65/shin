package com.example.shin.datas

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

import java.io.Serializable
import java.util.ArrayList
import java.util.Calendar


class User : Serializable {
    var id: Int = 0
    var type: String? = null
    var name: String? = null
    var phone_num: String? = null
    var phone_auth_num: String? = null
    var created_at: Calendar? = null
    var updated_at: Calendar? = null

    var attendance: String? = null

    var child: User? = null
    var studentInfo: StudentInfo? = null
    internal var parent: List<User> = ArrayList()


    companion object {

        fun getUserFromJson(json: JSONObject): User {
            val user = User()

            try {
                user.id = json.getInt("id")
                user.type = json.getString("type")
                user.name = json.getString("name")
                user.phone_num = json.getString("phone_num")

                if (!json.isNull("child") && json.getString("child") != "") {
                    user.child = User.getUserFromJson(json.getJSONObject("child"))
                }
                if (!json.isNull("student_info")) {
                    user.studentInfo =
                        StudentInfo.getInfoFromJson(json.getJSONObject("student_info"))
                }
                user.getParent().clear()
                if (!json.isNull("parents")) {
                    val parents = json.getJSONArray("parents")
                    for (i in 0 until parents.length()) {
                        user.getParent().add(User.getUserFromJson(parents.getJSONObject(i)))
                    }
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            return user
        }
    }

    fun User() {

    }

    fun getParent() : MutableList<User> {
        return parent.toMutableList() // <------------------
    }

    fun setParent(parent: List<User>) {
        this.parent = parent
    }

    fun GetId(): Int {
        return id
    }

    fun GetName(): String? {
        return name
    }

    fun GetPhone_num(): String? {
        return phone_num
    }

    fun GetChild(): User? {
        return child
    }
}