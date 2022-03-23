package com.mariomanzano.nasaexplorer.network.entities

import com.mariomanzano.domain.entities.EarthItem
import com.mariomanzano.domain.entities.MarsItem
import com.mariomanzano.domain.entities.PictureOfDayItem
import com.mariomanzano.nasaexplorer.data.utils.buildEarthImageUri
import com.mariomanzano.nasaexplorer.data.utils.toCalendar
import com.mariomanzano.nasaexplorer.ui.screens.common.DateFormatter
import java.util.*

fun ApiAPOD.asPictureOfTheDayItem() = PictureOfDayItem(
        id = 0,
        title = title ?: "",
        date = date.toCalendar(DateFormatter.Simple),
        description = explanation ?: "",
        url = url ?: "",
        copyRight = copyRight ?: ""
)

fun ApiEPIC.asEarthItem(): EarthItem {
        val cal = date.toCalendar(DateFormatter.FullTime)
        return EarthItem(
                id = 0,
                title = caption ?: "",
                date = cal,
                url = buildEarthImageUri(cal, image),
                description = ""
        )
}

fun ApiMarsItem.asMarsItem() = MarsItem(
        id = id ?: 0,
        sun = sol ?: 0,
        date = earth_date.toCalendar(DateFormatter.Simple),
        url = img_src ?: "",
        cameraName = camera.full_name ?: "",
        roverName = rover.name ?: "",
        roverLandingDate = rover.landing_date?.toCalendar(DateFormatter.Simple)
                ?: Calendar.getInstance(),
        roverLaunchingDate = rover.launching_date?.toCalendar(DateFormatter.Simple)
                ?: Calendar.getInstance(),
        roverMissionStatus = rover.status ?: "",
        description = "",
        title = ""
)
