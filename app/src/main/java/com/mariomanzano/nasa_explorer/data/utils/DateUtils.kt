package com.mariomanzano.nasa_explorer.data.utils

import java.text.SimpleDateFormat
import java.util.*


sealed class DateFormatter(val formatter: SimpleDateFormat) {
    object Simple : DateFormatter(SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()))
    object Time : DateFormatter(SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss", Locale.getDefault()))
}

fun String.toCalendar(): Calendar {
    val cal = Calendar.getInstance()
    val dateParse = DateFormatter.Simple.formatter.parse(this)
    return dateParse?.let { cal.apply { time = dateParse } } ?: cal
}

fun String.toCalendarWithTime(): Calendar {
    val cal = Calendar.getInstance()
    val dateParse = DateFormatter.Time.formatter.parse(this)
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