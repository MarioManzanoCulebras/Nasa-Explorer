package com.mariomanzano.nasa_explorer.data.entities

import java.util.*

data class EarthItem(
    override val id: Int,
    override val date: Calendar,
    override val title: String,
    override val description: String,
    override val url: String,
    override val favorite: Boolean = false
) : NasaItem
