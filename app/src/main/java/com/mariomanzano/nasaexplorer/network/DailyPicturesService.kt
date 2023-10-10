package com.mariomanzano.nasaexplorer.network

import com.mariomanzano.nasaexplorer.network.entities.ApiAPOD
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

interface DailyPicturesService {
    suspend fun getPictureOfTheDay(date: String): ApiAPOD

    suspend fun getPicturesOfDateRange(startDate: String, endDate: String): List<ApiAPOD>

}

class DailyPicturesServiceImpl @Inject constructor(private val ktor: HttpClient) :
    DailyPicturesService {
    override suspend fun getPictureOfTheDay(date: String): ApiAPOD {
        return ktor.get("/planetary/apod") {
            parameter("date", date)
        }.body()
    }

    override suspend fun getPicturesOfDateRange(startDate: String, endDate: String): List<ApiAPOD> {
        return ktor.get("/planetary/apod") {
            parameter("start_date", startDate)
            parameter("end_date", endDate)
        }.body()
    }
}