package com.mariomanzano.nasa_explorer.network

import com.mariomanzano.nasa_explorer.network.entities.ApiNasaItem
import com.mariomanzano.nasa_explorer.network.entities.ApiResponse
import retrofit2.http.GET

interface DailyPictureService {

    @GET("/planetary/apod")
    suspend fun getPictureOfTheDay() : ApiResponse<ApiNasaItem>
}