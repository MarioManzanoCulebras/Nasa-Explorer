package com.mariomanzano.nasaexplorer.network

import arrow.core.Either
import com.mariomanzano.domain.Error
import com.mariomanzano.domain.entities.MarsItem
import com.mariomanzano.nasaexplorer.datasource.MarsRemoteDataSource
import com.mariomanzano.nasaexplorer.network.entities.asMarsItem
import com.mariomanzano.nasaexplorer.ui.screens.common.DateFormatter
import java.util.*
import javax.inject.Inject

class MarsServerDataSource @Inject constructor(
    private val marsService: MarsService
) : MarsRemoteDataSource {

    override suspend fun findMarsItems(): Either<Error, List<MarsItem>> =
        tryCall {
            marsService
                .getMarsGalleryFromDate(
                    date = DateFormatter.FullTime.formatter.format(
                        (Calendar.getInstance().apply {
                            set(
                                Calendar.DAY_OF_MONTH,
                                Calendar.getInstance().get(Calendar.DAY_OF_MONTH) - 120
                            )
                        }).time
                    )
                )
                .photos
                .map { it.asMarsItem() }
                .sortedByDescending { it.date }
        }
}