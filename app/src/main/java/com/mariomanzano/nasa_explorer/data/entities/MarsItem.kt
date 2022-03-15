package com.mariomanzano.nasa_explorer.data.entities

import java.util.*

data class MarsItem(
    override val date: Calendar,
    override val id: Int? = null,
    override val title: String? = null,
    override val description: String? = null,
    override val url: String? = null,
    val idTime: Long = date.time.time + (id?.toLong() ?: 0L),
    val sun: Int?,
    val cameraName: String?,
    val roverName: String?,
    val roverLandingDate: Calendar?,
    val roverLaunchingDate: Calendar?,
    val roverMissionStatus: String?
) : NasaItem
