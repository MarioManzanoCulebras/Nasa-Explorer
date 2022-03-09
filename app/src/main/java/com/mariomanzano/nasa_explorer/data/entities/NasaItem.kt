package com.mariomanzano.nasa_explorer.data.entities

interface NasaItem {
    val id: Int
    val title: String
    val description: String
    val thumbnail: String
    val urls: List<Url>
}