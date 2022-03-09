package com.mariomanzano.nasa_explorer.network.entities

data class ApiNasaItem(
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: ApiThumbnail,
    val urls: List<ApiUrl>
)
