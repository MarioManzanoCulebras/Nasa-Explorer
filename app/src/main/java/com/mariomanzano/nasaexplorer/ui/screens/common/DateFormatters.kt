package com.mariomanzano.nasaexplorer.ui.screens.common

import java.text.SimpleDateFormat
import java.util.*

sealed class DateFormatter(val formatter: SimpleDateFormat) {
    object Simple : DateFormatter(SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()))
    object FullTime : DateFormatter(SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss", Locale.getDefault()))
    object OnlyTime : DateFormatter(SimpleDateFormat("HH:mm:ss", Locale.getDefault()))
}