package com.mariomanzano.nasa_explorer.network.entities

data class ApiAPOD(
    val copyRight: String?,
    val date: String,
    val title: String?,
    val explanation: String?,
    val media_type: String?,
    val url: String?
)
