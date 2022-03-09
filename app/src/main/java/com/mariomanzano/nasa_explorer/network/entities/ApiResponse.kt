package com.mariomanzano.nasa_explorer.network.entities

data class ApiResponse<T>(
    val attributionHTML: String,
    val attributionText: String,
    val code: Int,
    val copyright: String,
    val data: ApiData<T>,
    val etag: String,
    val status: String
)