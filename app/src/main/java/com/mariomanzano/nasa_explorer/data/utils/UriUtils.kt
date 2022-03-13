package com.mariomanzano.nasa_explorer.data.utils

import com.mariomanzano.nasa_explorer.BuildConfig
import java.util.*

fun buildEarthImageUri(cal: Calendar, imageNasaId: String?) =
    BuildConfig.EARTH_API_IMAGES_BASE_URL +
            cal.get(Calendar.YEAR) + "/" +
            (cal.get(Calendar.MONTH) + 1).monthApiCorrection() + "/" +
            cal.get(Calendar.DAY_OF_MONTH) +
            "/png/" + imageNasaId + ".png"