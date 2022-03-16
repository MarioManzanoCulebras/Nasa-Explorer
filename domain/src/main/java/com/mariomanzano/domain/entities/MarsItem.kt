package com.mariomanzano.domain.entities

import java.util.*

data class MarsItem(
    override val id: Int,
    override val date: Calendar,
    override val title: String,
    override val description: String,
    override val url: String,
    override val favorite: Boolean = false,
    val sun: Int,
    val cameraName: String,
    val roverName: String,
    val roverLandingDate: Calendar,
    val roverLaunchingDate: Calendar,
    val roverMissionStatus: String
) : NasaItem
