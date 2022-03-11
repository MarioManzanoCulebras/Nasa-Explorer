package com.mariomanzano.nasa_explorer.data.entities

import java.util.*

interface NasaItem {
    val date: Calendar
    val id: Int?
    val title: String?
    val description: String?
    val url: String?
}