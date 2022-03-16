package com.mariomanzano.nasaexplorer.network.entities

data class ApiAPOD(
    val copyRight: String?,
    val date: String,
    val title: String?,
    val explanation: String?,
    val media_type: String?,
    val url: String?
)
