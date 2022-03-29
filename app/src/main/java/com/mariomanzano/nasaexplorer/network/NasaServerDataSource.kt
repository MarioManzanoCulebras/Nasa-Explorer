package com.mariomanzano.nasaexplorer.network

import arrow.core.Either
import com.mariomanzano.domain.Error
import com.mariomanzano.domain.entities.EarthItem
import com.mariomanzano.domain.entities.MarsItem
import com.mariomanzano.domain.entities.PictureOfDayItem
import com.mariomanzano.nasaexplorer.datasource.EarthRemoteDataSource
import com.mariomanzano.nasaexplorer.datasource.MarsRemoteDataSource
import com.mariomanzano.nasaexplorer.datasource.PODRemoteDataSource
import com.mariomanzano.nasaexplorer.network.entities.asEarthItem
import com.mariomanzano.nasaexplorer.network.entities.asMarsItem
import com.mariomanzano.nasaexplorer.network.entities.asPictureOfTheDayItem
import com.mariomanzano.nasaexplorer.ui.screens.common.DateFormatter
import java.util.*

class NasaServerDataSource : PODRemoteDataSource,
    EarthRemoteDataSource,
    MarsRemoteDataSource {

    override suspend fun findPODDay(date: Calendar): Either<Error, PictureOfDayItem> =
        tryCall {
            ApiClient
                .dailyPicturesService
                .getPictureOfTheDay(DateFormatter.Simple.formatter.format(date.time))
                .asPictureOfTheDayItem()
        }

    override suspend fun findPODitems(
        from: Calendar,
        to: Calendar
    ): Either<Error, List<PictureOfDayItem>> =
        tryCall {
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

    override suspend fun findEarthItems(): Either<Error, List<EarthItem>> =
        tryCall {
            ApiClient
                .dailyEarthService
                .getDailyEarth()
                .map { it.asEarthItem() }
                .sortedByDescending { it.date }
        }

    override suspend fun findMarsItems(): Either<Error, List<MarsItem>> =
        tryCall {
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