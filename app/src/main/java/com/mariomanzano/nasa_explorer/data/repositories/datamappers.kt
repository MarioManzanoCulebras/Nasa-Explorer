package com.mariomanzano.nasa_explorer.data.repositories

import com.mariomanzano.nasa_explorer.data.entities.PictureOfDayItem
import com.mariomanzano.nasa_explorer.network.entities.ApiNasaItem

fun ApiNasaItem.asPictureOfTheDayItem() = PictureOfDayItem(id)