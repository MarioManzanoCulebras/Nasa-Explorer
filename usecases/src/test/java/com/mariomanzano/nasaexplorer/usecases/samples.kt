package com.mariomanzano.nasaexplorer.usecases

import com.mariomanzano.domain.entities.EarthItem
import com.mariomanzano.domain.entities.LastUpdateInfo
import com.mariomanzano.domain.entities.MarsItem
import com.mariomanzano.domain.entities.PictureOfDayItem
import java.util.*

internal val samplePOD = PictureOfDayItem(
    id = 0,
    date = Calendar.getInstance(),
    title = "Title",
    description = "Description",
    url = "url",
    mediaType = "image",
    favorite = false,
    copyRight = "copyRight"
)

internal val sampleEarth = EarthItem(
    id = 0,
    date = Calendar.getInstance(),
    title = "Title",
    description = "Description",
    url = "url",
    mediaType = "image",
    favorite = false
)

internal val sampleMars = MarsItem(
    id = 0,
    date = Calendar.getInstance(),
    title = "Title",
    description = "Description",
    url = "url",
    mediaType = "image",
    favorite = false,
    sun = 0,
    cameraName = "camera",
    roverName = "rover",
    roverLandingDate = Calendar.getInstance(),
    roverLaunchingDate = Calendar.getInstance(),
    roverMissionStatus = "MissionStatus"
)

internal val sampleLastUpdateInfo = LastUpdateInfo(
    id = 0,
    date = Calendar.getInstance(),
    updateNeed = false
)
