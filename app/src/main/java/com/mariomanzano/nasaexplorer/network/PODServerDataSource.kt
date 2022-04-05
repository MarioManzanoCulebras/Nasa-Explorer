package com.mariomanzano.nasaexplorer.network

import arrow.core.Either
import com.mariomanzano.domain.Error
import com.mariomanzano.domain.entities.PictureOfDayItem
import com.mariomanzano.nasaexplorer.datasource.PODRemoteDataSource
import com.mariomanzano.nasaexplorer.network.entities.asPictureOfTheDayItem
import com.mariomanzano.nasaexplorer.ui.screens.common.DateFormatter
import java.util.*
import javax.inject.Inject

class PODServerDataSource @Inject constructor(
    private val dailyPicturesService: DailyPicturesService
) : PODRemoteDataSource {

    override suspend fun findPODDay(date: Calendar): Either<Error, PictureOfDayItem> =
        tryCall {
            dailyPicturesService
                .getPictureOfTheDay(DateFormatter.Simple.formatter.format(date.time))
                .asPictureOfTheDayItem()
        }

    override suspend fun findPODitems(
        from: Calendar,
        to: Calendar
    ): Either<Error, List<PictureOfDayItem>> =
        tryCall {
            dailyPicturesService
                .getPicturesOfDateRange(
                    startDate = DateFormatter.Simple.formatter.format(from.time),
                    endDate = DateFormatter.Simple.formatter.format(to.time)
                )
                .map { it.asPictureOfTheDayItem() }
                .sortedByDescending { it.date }
        }
}