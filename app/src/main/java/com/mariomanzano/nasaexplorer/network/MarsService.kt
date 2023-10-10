package com.mariomanzano.nasaexplorer.network

import com.mariomanzano.nasaexplorer.network.entities.ApiMars
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

interface MarsService {

    suspend fun getMarsGalleryFromSun(sol: Int): ApiMars

    suspend fun getMarsGalleryFromDate(date: String): ApiMars
}

class MarsServiceImpl @Inject constructor(private val ktor: HttpClient) : MarsService {

    override suspend fun getMarsGalleryFromSun(sol: Int): ApiMars {
        return ktor.get("/mars-photos/api/v1/rovers/curiosity/photos") {
            parameter("sol", sol)
        }.body()
    }

    override suspend fun getMarsGalleryFromDate(date: String): ApiMars {
        return ktor.get("/mars-photos/api/v1/rovers/curiosity/photos") {
            parameter("earth_date", date)
        }.body()
    }
}