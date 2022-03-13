package com.mariomanzano.nasa_explorer.network

import com.mariomanzano.nasa_explorer.network.entities.ApiEPIC
import retrofit2.http.GET
import retrofit2.http.Query

interface DailyEarthService {

    @GET("/EPIC/api/natural/date")
    suspend fun getDailyEarthFromDate(@Query("date") date: String): ApiEPIC

    @GET("/EPIC/api/natural/images")
    suspend fun getDailyEarth(): List<ApiEPIC>
}