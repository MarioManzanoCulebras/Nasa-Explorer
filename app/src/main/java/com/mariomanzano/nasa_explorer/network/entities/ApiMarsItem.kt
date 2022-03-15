package com.mariomanzano.nasa_explorer.network.entities

data class ApiMarsItem(
    val id: Int?,
    val sol: Int?,
    val img_src: String?,
    val earth_date: String,
    val camera: ApiMarsCamera,
    val rover: ApiMarsRover
)
