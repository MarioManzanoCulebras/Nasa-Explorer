package com.mariomanzano.nasa_explorer.network

import com.mariomanzano.nasa_explorer.network.entities.ApiMars
import retrofit2.http.GET
import retrofit2.http.Query

interface MarsService {

    @GET("/mars-photos/api/v1/rovers/curiosity/photos")
    suspend fun getMarsGalleryFromSun(@Query("sol") sol: Int): ApiMars

    @GET("/mars-photos/api/v1/rovers/curiosity/photos")
    suspend fun getMarsGalleryFromDate(@Query("earth_date") date: String): ApiMars
}