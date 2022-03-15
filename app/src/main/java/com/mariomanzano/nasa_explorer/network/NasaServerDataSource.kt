package com.mariomanzano.nasa_explorer.network

import arrow.core.Either
import com.mariomanzano.nasa_explorer.data.datasource.EarthRemoteDataSource
import com.mariomanzano.nasa_explorer.data.datasource.MarsRemoteDataSource
import com.mariomanzano.nasa_explorer.data.datasource.PODRemoteDataSource
import com.mariomanzano.nasa_explorer.data.entities.*
import com.mariomanzano.nasa_explorer.data.repositories.asEarthItem
import com.mariomanzano.nasa_explorer.data.repositories.asMarsItem
import com.mariomanzano.nasa_explorer.data.repositories.asPictureOfTheDayItem
import com.mariomanzano.nasa_explorer.ui.screens.common.DateFormatter
import java.util.*

class MovieServerDataSource : PODRemoteDataSource, EarthRemoteDataSource, MarsRemoteDataSource {

    override suspend fun findPODitems(
        from: Calendar,
        to: Calendar
    ): Either<Error, List<PictureOfDayItem>> = tryCall {
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

    override suspend fun findEarthItems(): Either<Error, List<EarthItem>> = tryCall {
        ApiClient
            .dailyEarthService
            .getDailyEarth()
            .map { it.asEarthItem() }
            .sortedByDescending { it.date }
    }

    override suspend fun findMarsItems(): Either<Error, List<MarsItem>> = tryCall {
        ApiClient
            .marsService
            .getMarsGalleryFromDate(
                date = DateFormatter.FullTime.formatter.format(
                    (Calendar.getInstance().apply {
                        set(
                            Calendar.DAY_OF_MONTH,
                            Calendar.getInstance().get(Calendar.DAY_OF_MONTH) - 59
                        )
                    }).time
                )
            )
            .photos
            .map { it.asMarsItem() }
            .sortedByDescending { it.date }
    }
}