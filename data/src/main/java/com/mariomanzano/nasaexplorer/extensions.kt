package com.mariomanzano.nasaexplorer

import java.util.*

fun Calendar.sameDay(date: Calendar): Boolean {
    val dayOfMonth = this.get(Calendar.DAY_OF_MONTH)
    val month = this.get(Calendar.MONTH)
    val year = this.get(Calendar.YEAR)
    return dayOfMonth == date.get(Calendar.DAY_OF_MONTH) &&
            month == date.get(Calendar.MONTH) &&
            year == date.get(Calendar.YEAR)
}