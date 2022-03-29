package com.mariomanzano.nasaexplorer.data.utils

import com.mariomanzano.nasaexplorer.ui.screens.common.DateFormatter
import java.util.*

fun String.toCalendar(formatter: DateFormatter): Calendar {
    val cal = Calendar.getInstance()
    val dateParse = formatter.formatter.parse(this)
    return dateParse?.let { cal.apply { time = dateParse } } ?: cal
}

//Api request for a MM format
fun Int.monthApiCorrection(): String {
    return if (this < 10) {
        "0$this"
    } else {
        this.toString()
    }
}

fun Calendar.checkIfDayAfterToday(): Boolean {
    val currentDay = Calendar.getInstance()
    val dayOfMonth = this.get(Calendar.DAY_OF_MONTH)
    val month = this.get(Calendar.MONTH)
    val year = this.get(Calendar.YEAR)
    return dayOfMonth != currentDay.get(Calendar.DAY_OF_MONTH) ||
            month != currentDay.get(Calendar.MONTH) ||
            year != currentDay.get(Calendar.YEAR)
}