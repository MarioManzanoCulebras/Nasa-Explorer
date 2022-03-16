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