package com.mariomanzano.nasa_explorer.network

import com.mariomanzano.nasa_explorer.network.entities.ApiNasaItem
import com.mariomanzano.nasa_explorer.network.entities.ApiResponse

interface DailyPictureService {
    suspend fun getPictureOfTheDay() : ApiResponse<ApiNasaItem>
}