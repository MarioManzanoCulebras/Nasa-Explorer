package com.mariomanzano.nasa_explorer.data.repositories

import com.mariomanzano.nasa_explorer.data.entities.EarthItem
import com.mariomanzano.nasa_explorer.data.entities.MarsItem
import com.mariomanzano.nasa_explorer.data.entities.PictureOfDayItem
import com.mariomanzano.nasa_explorer.data.utils.buildEarthImageUri
import com.mariomanzano.nasa_explorer.data.utils.toCalendar
import com.mariomanzano.nasa_explorer.network.entities.ApiAPOD
import com.mariomanzano.nasa_explorer.network.entities.ApiEPIC
import com.mariomanzano.nasa_explorer.network.entities.ApiMarsItem
import com.mariomanzano.nasa_explorer.ui.screens.common.DateFormatter

fun ApiAPOD.asPictureOfTheDayItem() = PictureOfDayItem(
        id = 0,
        title = title,
        date = date.toCalendar(DateFormatter.Simple),
        description = explanation,
        url = url
)

fun ApiEPIC.asEarthItem(): EarthItem {
        val cal = date.toCalendar(DateFormatter.FullTime)
        return EarthItem(
                id = 0,
                title = caption,
                date = cal,
                url = buildEarthImageUri(cal, image)
        )
}

fun ApiMarsItem.asMarsItem() = MarsItem(
        id = id,
        sun = sol,
        date = earth_date.toCalendar(DateFormatter.Simple),
        url = img_src,
        cameraName = camera.full_name,
        roverName = rover.name,
        roverLandingDate = rover.landing_date?.toCalendar(DateFormatter.Simple),
        roverLaunchingDate = rover.launching_date?.toCalendar(DateFormatter.Simple),
        roverMissionStatus = rover.status
)
