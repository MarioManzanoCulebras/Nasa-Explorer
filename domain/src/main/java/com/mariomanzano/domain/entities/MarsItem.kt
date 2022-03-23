package com.mariomanzano.domain.entities

import java.util.*

data class MarsItem(
    override var id: Int,
    override val date: Calendar,
    override val title: String,
    override val description: String,
    override val url: String,
    override var favorite: Boolean = false,
    override val type: String = "mars",
    val sun: Int,
    val cameraName: String,
    val roverName: String,
    val roverLandingDate: Calendar,
    val roverLaunchingDate: Calendar,
    val roverMissionStatus: String
) : NasaItem
