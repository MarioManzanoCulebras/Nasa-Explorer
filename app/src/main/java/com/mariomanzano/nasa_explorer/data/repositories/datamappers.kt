package com.mariomanzano.nasa_explorer.data.repositories

import com.mariomanzano.nasa_explorer.data.entities.PictureOfDayItem
import com.mariomanzano.nasa_explorer.data.entities.Url
import com.mariomanzano.nasa_explorer.network.entities.ApiNasaItem

fun ApiNasaItem.asPictureOfTheDayItem() = PictureOfDayItem(
    id,
    name,
    description,
    thumbnail.toString(),
    urls.map { Url(it.type, it.url) }
)