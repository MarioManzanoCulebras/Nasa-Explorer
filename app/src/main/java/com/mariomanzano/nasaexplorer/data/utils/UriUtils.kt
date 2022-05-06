package com.mariomanzano.nasaexplorer.data.utils

import java.util.*

fun buildEarthImageUri(cal: Calendar, imageNasaId: String?) =
    "https://epic.gsfc.nasa.gov/archive/natural/" +
            cal.get(Calendar.YEAR) + "/" +
            (cal.get(Calendar.MONTH) + 1).monthApiCorrection() + "/" +
            cal.get(Calendar.DAY_OF_MONTH) +
            "/png/" + imageNasaId + ".png"