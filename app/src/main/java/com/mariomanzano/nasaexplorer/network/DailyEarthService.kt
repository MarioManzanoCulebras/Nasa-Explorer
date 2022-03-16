package com.mariomanzano.nasaexplorer.network

import com.mariomanzano.nasaexplorer.network.entities.ApiEPIC
import retrofit2.http.GET
import retrofit2.http.Query

interface DailyEarthService {

    @GET("/EPIC/api/natural/date")
    suspend fun getDailyEarthFromDate(@Query("date") date: String): ApiEPIC

    @GET("/EPIC/api/natural/images")
    suspend fun getDailyEarth(): List<ApiEPIC>
}