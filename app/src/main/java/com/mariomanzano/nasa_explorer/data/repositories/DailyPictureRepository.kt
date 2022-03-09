package com.mariomanzano.nasa_explorer.data.repositories
import com.mariomanzano.nasa_explorer.data.entities.PictureOfDayItem
import com.mariomanzano.nasa_explorer.data.entities.Result
import com.mariomanzano.nasa_explorer.network.ApiClient

object DailyPictureRepository : Repository<PictureOfDayItem>() {

    suspend fun get(): Result<List<PictureOfDayItem>> = super.get {
        ApiClient
            .dailyPictureService
            .getPictureOfTheDay()
            .data
            .results
            .map { it.asPictureOfTheDayItem() }
    }
}