package com.mariomanzano.domain.entities

import java.util.*

data class PictureOfDayItem(
    override var id: Int,
    override val date: Calendar,
    override val title: String,
    override val description: String,
    override val url: String,
    override val mediaType: String,
    override var favorite: Boolean = false,
    override val type: String = "dailyPicture",
    val copyRight: String
): NasaItem
