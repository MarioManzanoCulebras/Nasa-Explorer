package com.mariomanzano.domain.entities

import java.util.*

data class PictureOfDayItem(
    override val id: Int,
    override val date: Calendar,
    override val title: String,
    override val description: String,
    override val url: String,
    override val favorite: Boolean = false,
    override var lastRequest: Calendar,
    val copyRight: String
): NasaItem
