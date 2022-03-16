package com.mariomanzano.nasaexplorer.network

import com.mariomanzano.nasaexplorer.network.entities.ApiMars
import retrofit2.http.GET
import retrofit2.http.Query

interface MarsService {

    @GET("/mars-photos/api/v1/rovers/curiosity/photos")
    suspend fun getMarsGalleryFromSun(@Query("sol") sol: Int): ApiMars

    @GET("/mars-photos/api/v1/rovers/curiosity/photos")
    suspend fun getMarsGalleryFromDate(@Query("earth_date") date: String): ApiMars
}