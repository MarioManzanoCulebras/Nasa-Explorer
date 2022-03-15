package com.mariomanzano.nasa_explorer.data.entities

import java.util.*

data class PictureOfDayItem(
    override val id: Int,
    override val date: Calendar,
    override val title: String,
    override val description: String,
    override val url: String,
    override val favorite: Boolean = false,
    val copyRight: String
): NasaItem
