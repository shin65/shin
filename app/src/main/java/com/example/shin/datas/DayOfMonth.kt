package com.example.shin.datas

import java.io.Serializable
import java.util.Calendar


class DayOfMonth : Serializable {
    var day: Int = 0
    var isSelected: Boolean = false
    var date: Calendar? = null

    fun GetDay(): Int {
        return day
    }

    fun SetDay(day: Int) {
        this.day = day
    }

    fun IsSelected(): Boolean {
        return isSelected
    }

    fun SetSelected(selected: Boolean) {
        isSelected = selected
    }

    fun GetDate(): Calendar? {
        return date
    }

    fun SetDate(date: Calendar) {
        this.date = date
    }
}
