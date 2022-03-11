package com.mariomanzano.nasa_explorer.data.repositories

import com.mariomanzano.nasa_explorer.data.entities.PictureOfDayItem
import com.mariomanzano.nasa_explorer.data.entities.Url
import com.mariomanzano.nasa_explorer.network.entities.ApiAPOD
import com.mariomanzano.nasa_explorer.ui.screens.common.toCalendar
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

fun ApiAPOD.asPictureOfTheDayItem() = PictureOfDayItem(
        id = 0,
        title = title,
        date = date.toCalendar(),
        description = explanation,
        url = url
)

