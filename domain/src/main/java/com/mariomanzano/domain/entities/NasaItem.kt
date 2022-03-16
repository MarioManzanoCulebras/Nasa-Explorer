package com.mariomanzano.domain.entities

import java.util.*

interface NasaItem {
    val id: Int?
    val date: Calendar
    val title: String?
    val description: String?
    val url: String?
    val favorite: Boolean
}