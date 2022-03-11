package com.mariomanzano.nasa_explorer.network

import com.mariomanzano.nasa_explorer.BuildConfig
import com.mariomanzano.nasa_explorer.network.entities.ApiAPOD
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

interface DailyPicturesService {

    @GET("/planetary/apod")
    suspend fun getPictureOfTheDay(@Query("date") date: String) : ApiAPOD

    @GET("/planetary/apod")
    suspend fun getPicturesOfDateRange(@Query("start_date") startDate: String, @Query("end_date") endDate: String) : List<ApiAPOD>
}