package com.mariomanzano.nasa_explorer.data.utils

import com.mariomanzano.nasa_explorer.ui.screens.common.DateFormatter
import java.util.*

fun String.toCalendar(): Calendar {
    val cal = Calendar.getInstance()
    val dateParse = DateFormatter.Simple.formatter.parse(this)
    return dateParse?.let { cal.apply { time = dateParse } } ?: cal
}

fun String.toCalendarWithTime(): Calendar {
    val cal = Calendar.getInstance()
    val dateParse = DateFormatter.FullTime.formatter.parse(this)
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