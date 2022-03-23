package com.mariomanzano.domain.entities

import java.util.*

interface NasaItem {
    var id: Int
    val date: Calendar
    val title: String?
    val description: String?
    val url: String?
    var favorite: Boolean
    val type: String
}