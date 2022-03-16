package com.mariomanzano.nasaexplorer.data.database

import androidx.room.TypeConverter
import java.util.*

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long): Calendar {
        return value.let { Calendar.getInstance().apply { time = Date(value) } }
    }

    @TypeConverter
    fun dateToTimestamp(date: Calendar): Long {
        return date.time.time
    }
}