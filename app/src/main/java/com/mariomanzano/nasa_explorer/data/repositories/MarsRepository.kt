package com.mariomanzano.nasa_explorer.data.repositories

import com.mariomanzano.nasa_explorer.data.entities.MarsItem
import com.mariomanzano.nasa_explorer.data.entities.Result
import com.mariomanzano.nasa_explorer.network.ApiClient
import com.mariomanzano.nasa_explorer.ui.screens.common.DateFormatter
import java.util.*

object MarsRepository : Repository<MarsItem>() {

    suspend fun get(): Result<List<MarsItem>> = super.get {
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

    suspend fun find(date: Calendar = Calendar.getInstance()): Result<MarsItem> =
        super.find(date) {
            ApiClient
                .marsService
                .getMarsGalleryFromDate(date = DateFormatter.FullTime.formatter.format(date.time))
                .photos
                .map { it.asMarsItem() }
                .sortedByDescending { it.date }.first()
        }
}