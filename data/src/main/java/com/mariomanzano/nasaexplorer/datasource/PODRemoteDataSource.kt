package com.mariomanzano.nasaexplorer.datasource

import arrow.core.Either
import com.mariomanzano.domain.Error
import com.mariomanzano.domain.entities.PictureOfDayItem
import java.util.*

interface PODRemoteDataSource {

    suspend fun findPODDay(
        date: Calendar = Calendar.getInstance()
    ): Either<Error, PictureOfDayItem>

    suspend fun findPODitems(
        from: Calendar = Calendar.getInstance().apply {
            set(
                Calendar.DAY_OF_MONTH,
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH) - 59
            )
        },
        to: Calendar = Calendar.getInstance()
    ): Either<Error, List<PictureOfDayItem>>
}