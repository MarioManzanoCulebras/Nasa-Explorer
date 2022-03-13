package com.mariomanzano.nasa_explorer.data.repositories

import com.mariomanzano.nasa_explorer.data.entities.EarthItem
import com.mariomanzano.nasa_explorer.data.entities.Result
import com.mariomanzano.nasa_explorer.network.ApiClient
import com.mariomanzano.nasa_explorer.ui.screens.common.DateFormatter
import java.util.*

object DailyEarthRepository : Repository<EarthItem>() {

    suspend fun get(): Result<List<EarthItem>> = super.get {
        ApiClient
            .dailyEarthService
            .getDailyEarth()
            .map { it.asEarthItem() }
            .sortedByDescending { it.date }
    }

    suspend fun find(date: Calendar = Calendar.getInstance()): Result<EarthItem> =
        super.find(date) {
            ApiClient
                .dailyEarthService
                .getDailyEarthFromDate(date = DateFormatter.FullTime.formatter.format(date.time))
                .asEarthItem()
        }
}