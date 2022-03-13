package com.mariomanzano.nasa_explorer.data.repositories

import com.mariomanzano.nasa_explorer.data.entities.EarthItem
import com.mariomanzano.nasa_explorer.data.entities.PictureOfDayItem
import com.mariomanzano.nasa_explorer.data.utils.buildEarthImageUri
import com.mariomanzano.nasa_explorer.data.utils.toCalendar
import com.mariomanzano.nasa_explorer.data.utils.toCalendarWithTime
import com.mariomanzano.nasa_explorer.network.entities.ApiAPOD
import com.mariomanzano.nasa_explorer.network.entities.ApiEPIC

fun ApiAPOD.asPictureOfTheDayItem() = PictureOfDayItem(
        id = 0,
        title = title,
        date = date.toCalendar(),
        description = explanation,
        url = url
)

fun ApiEPIC.asEarthItem(): EarthItem {
        val cal = date.toCalendarWithTime()
        return EarthItem(
                id = 0,
                title = caption,
                date = cal,
                url = buildEarthImageUri(cal, image)
        )
}
