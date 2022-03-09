package com.mariomanzano.nasa_explorer.data.entities

data class PictureOfDayItem(
    override val id: Int,
    override val title: String,
    override val description: String,
    override val thumbnail: String,
    override val urls: List<Url>
): NasaItem
