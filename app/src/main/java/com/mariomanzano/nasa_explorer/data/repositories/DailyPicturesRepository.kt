package com.mariomanzano.nasa_explorer.data.repositories
import com.mariomanzano.nasa_explorer.data.entities.PictureOfDayItem
import com.mariomanzano.nasa_explorer.data.entities.Result
import com.mariomanzano.nasa_explorer.network.ApiClient
import com.mariomanzano.nasa_explorer.ui.screens.common.DateFormatter
import java.util.*

object DailyPicturesRepository : Repository<PictureOfDayItem>() {

    suspend fun get(
        from: Calendar = Calendar.getInstance().apply {
            set(
                Calendar.DAY_OF_MONTH,
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH) - 59
            )
        },
        to: Calendar = Calendar.getInstance()
    ): Result<List<PictureOfDayItem>> = super.get {
        ApiClient
            .dailyPicturesService
            .getPicturesOfDateRange(
                startDate = DateFormatter.Simple.formatter.format(from.time),
                endDate = DateFormatter.Simple.formatter.format(to.time)
            )
            .filter { it.media_type == "image" }
            .map { it.asPictureOfTheDayItem() }
            .sortedByDescending { it.date }
    }

    suspend fun find(date: Calendar = Calendar.getInstance()) : Result<PictureOfDayItem> = super.find(date) {
        ApiClient
            .dailyPicturesService
            .getPictureOfTheDay(date = DateFormatter.Simple.formatter.format(date.time))
            .asPictureOfTheDayItem()
    }
}