package com.mariomanzano.nasaexplorer.network

import com.mariomanzano.nasaexplorer.network.entities.ApiAPOD
import retrofit2.http.GET
import retrofit2.http.Query

interface DailyPicturesService {

    @GET("/planetary/apod")
    suspend fun getPictureOfTheDay(@Query("date") date: String) : ApiAPOD

    @GET("/planetary/apod")
    suspend fun getPicturesOfDateRange(@Query("start_date") startDate: String, @Query("end_date") endDate: String) : List<ApiAPOD>
}