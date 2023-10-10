package com.mariomanzano.nasaexplorer.network

import com.mariomanzano.nasaexplorer.network.entities.ApiEPIC
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

interface DailyEarthService {
    suspend fun getDailyEarthFromDate(date: String): ApiEPIC
    suspend fun getDailyEarth(): List<ApiEPIC>
}

class DailyEarthServiceImpl @Inject constructor(private val ktor: HttpClient) : DailyEarthService {
    override suspend fun getDailyEarthFromDate(date: String): ApiEPIC {
        return ktor.get("/EPIC/api/natural/date") {
            parameter("date", date)
        }.body()
    }

    override suspend fun getDailyEarth(): List<ApiEPIC> {
        return ktor.get("/EPIC/api/natural/images").body()
    }
}