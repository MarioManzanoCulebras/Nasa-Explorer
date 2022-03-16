package com.mariomanzano.nasaexplorer.data.utils

import com.mariomanzano.nasaexplorer.BuildConfig
import java.util.*

fun buildEarthImageUri(cal: Calendar, imageNasaId: String?) =
    BuildConfig.EARTH_API_IMAGES_BASE_URL +
            cal.get(Calendar.YEAR) + "/" +
            (cal.get(Calendar.MONTH) + 1).monthApiCorrection() + "/" +
            cal.get(Calendar.DAY_OF_MONTH) +
            "/png/" + imageNasaId + ".png"