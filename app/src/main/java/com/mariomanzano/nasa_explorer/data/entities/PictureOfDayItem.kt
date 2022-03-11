package com.mariomanzano.nasa_explorer.data.entities

import java.util.*

data class PictureOfDayItem(
    override val date: Calendar,
    override val id: Int? = null,
    override val title: String? = null,
    override val description: String? = null,
    override val url: String? = null,
    val copyRight: String? = null,
    val idTime: Long = date.time.time,
): NasaItem
