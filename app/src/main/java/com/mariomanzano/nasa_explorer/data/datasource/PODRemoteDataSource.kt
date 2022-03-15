package com.mariomanzano.nasa_explorer.data.datasource

import arrow.core.Either
import com.mariomanzano.nasa_explorer.data.entities.Error
import com.mariomanzano.nasa_explorer.data.entities.PictureOfDayItem
import java.util.*

interface PODRemoteDataSource {
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